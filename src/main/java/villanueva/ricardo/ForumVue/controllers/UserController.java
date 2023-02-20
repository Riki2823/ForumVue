package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import villanueva.ricardo.ForumVue.model.User;
import villanueva.ricardo.ForumVue.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

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
}
