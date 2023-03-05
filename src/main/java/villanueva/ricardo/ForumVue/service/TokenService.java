package villanueva.ricardo.ForumVue.service;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import villanueva.ricardo.ForumVue.model.User;

import java.time.Instant;
import java.util.*;

@Service
public class TokenService {
    @Autowired
    UserService userService;
    @Autowired
    CategoriesService categoriesService;

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
        json.put("iat", createIat());

        Map<String, Object> permissions = new HashMap<>();
        List<String> root = userService.getPermissions(user.getRole());
        Map<String, Object> categories = categoriesService.getCategoriesModerates(user);

        permissions.put("root", root);
        permissions.put("categories", categories);

        json.put("permissions", permissions);

        String token = JWT.create()
                .withPayload(json)
                .sign(Algorithm.HMAC512(tokenSecet.getBytes()));
        return token;
    }

    private long createIat() {
        Date issuedAt =Date.from( Instant.now());
        return issuedAt.getTime() / 1000;
    }

    public String getEmail(String token) {
        String algo =  JWT.require(Algorithm.HMAC512(tokenSecet.getBytes())).build().verify(token).getClaim("email").asString();
        return algo;
    }

    public boolean isAdmin(String token) {
        String role = JWT.require(Algorithm.HMAC512(tokenSecet.getBytes())).build().verify(token).getClaim("role").asString();
        System.out.println(role);
        if (role.equals("admin")){
            return true;
        }else {
            return false;
        }
    }
}
