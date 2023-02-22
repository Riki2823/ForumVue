package villanueva.ricardo.ForumVue.service;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import villanueva.ricardo.ForumVue.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TokenService {

    @Value("${token.secret}")
    String tokenSecet;

    @Value("${token.expiration}")
    String tokenExpiration;

    public String newToken(User user){
        Map<String, Object> json  = new HashMap<>();
        json.put("role", user.getRole());
        json.put("_id", user.getId());
        json.put("email", user.getEmail());
        json.put("name", user.getName());
        String token = JWT.create()
                .withPayload(json)
                .sign(Algorithm.HMAC512(tokenSecet.getBytes()));
        return token;
    }

    public String getEmail(String token) {
        String algo =  JWT.require(Algorithm.HMAC512(tokenSecet.getBytes())).build().verify(token).getClaim("email").asString();
        return algo;
    }
}
