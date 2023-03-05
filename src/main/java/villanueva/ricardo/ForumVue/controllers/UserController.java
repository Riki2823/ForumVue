package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import villanueva.ricardo.ForumVue.model.User;
import villanueva.ricardo.ForumVue.service.CategoriesService;
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
    @Autowired
    CategoriesService categoriesService;


    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
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

    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
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

        Map<String,Object> userResponse = new HashMap<>();
        userResponse.put("role", u.getRole());
        userResponse.put("_id", u.getId());
        userResponse.put("email", u.getEmail());
        userResponse.put("name", u.getName());
        userResponse.put("__v", 0);
        userResponse.put("avatarUrl", "");
        userResponse.put("id", u.getId());


        Map<String, Object> permissions= new HashMap<>();
        List<String> root = userService.getPermissions(u.getRole());
        Map<String, Object> categories =  categoriesService.getCategoriesModerates(u);

        permissions.put("root", root);
        permissions.put("categories", categories);

        userResponse.put("permissions", permissions);

        response.put("user", userResponse);
        response.put("token", token);

        return response;
    }

    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
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
        List<String> root = userService.getPermissions(user.getRole());

        permissions.put("root", root);
        response.put("permissions", permissions);
        return response;
    }
}
