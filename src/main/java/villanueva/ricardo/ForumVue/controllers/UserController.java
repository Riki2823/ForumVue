package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import villanueva.ricardo.ForumVue.model.User;
import villanueva.ricardo.ForumVue.service.TokenService;
import villanueva.ricardo.ForumVue.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;


    @CrossOrigin(origins = {"http://localhost:3000"})
    @PostMapping("/register")
    public Map<String, String> makeLogin(@RequestBody User user, HttpServletResponse resp){
        User u = new User();
        HashMap<String, String> response = new HashMap<>();
        List<User> users = userService.findByEmail(user.getEmail());
        if (users.size() > 0){
            response.put("message", "This user already exists");
            resp.setStatus(400);
            return response;
        }
        BeanUtils.copyProperties(user, u);
        userService.addUser(u);
        response.put("message", "done");
        return response;
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @PostMapping("/login")
    public Map<String, Object> login (@RequestBody User user, HttpServletResponse resp, HttpSession session){
        User u = new User();
        HashMap<String, Object  > response = new HashMap<>();
        List<User> users = userService.findByEmail(user.getEmail());
        if (users.size() <= 0){
            response.put("message", "Incorrect email o password");
            resp.setStatus(400);
            return response;
        } else {
            BeanUtils.copyProperties(user, u);
            if (!users.get(0).getPassword().equals(u.getPassword())) {
                response.put("message", "Incorrect email o password");
                resp.setStatus(400);
                return response;
            }

        }
        u = users.get(0);
        String token = tokenService.newToken(u);
        session.setAttribute("user", u.getEmail());
        response.put("token", token);
        response.put("user", u);
        return response;
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @GetMapping("/getprofile")
    public Map<String, Object> getProfile(HttpServletRequest request){

        String userM = (String) request.getAttribute("email");
        List<User> users = userService.findByEmail(userM);
        User user = users.get(0);

        Map<String, Object> response = new HashMap<>();
        response.put("avatarUrl", "");
        response.put("email", user.getEmail());
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("role", user.getRole());
        response.put("_id", user.getId());
        Map<String, Object> permissions = new HashMap<>();
        List<String> root = new ArrayList<>();
        switch (user.getRole()) {
            case "admin":
                root.add("own_topics:write");
                root.add("own_topics:delete");
                root.add("own_replies:write");
                root.add("own_replies:delete");
                root.add("categories:write");
                root.add("categories:delete");
            break;
            case "user":
                root.add("own_topics:write");
                root.add("own_topics:delete");
                root.add("own_replies:write");
                root.add("own_replies:delete");
            break;
        }
        permissions.put("root", root);
        response.put("permissions", permissions);
        return response;
    }
}
