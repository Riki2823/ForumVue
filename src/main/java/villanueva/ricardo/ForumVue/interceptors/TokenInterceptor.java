package villanueva.ricardo.ForumVue.interceptors;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import villanueva.ricardo.ForumVue.service.TokenService;
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")){
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = tokenService.getEmail(token);
            request.setAttribute("email", email);
            return true;
        }catch (SignatureVerificationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
