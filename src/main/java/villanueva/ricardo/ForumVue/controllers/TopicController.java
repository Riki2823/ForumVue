package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.model.Topics;
import villanueva.ricardo.ForumVue.service.CategoriesService;
import villanueva.ricardo.ForumVue.service.TokenService;
import villanueva.ricardo.ForumVue.service.TopicsService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TopicController {
    @Autowired
    TokenService tokenService;

    @Autowired
    TopicsService topicsService;
    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/categories/{catSlug}/topics")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public String[] returnTopics(@PathVariable String catSlug){
        String[] response = new String[0];
        return response;
    }

    @PostMapping("/topics")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public Map<String, Object> createTopic(HttpServletResponse resp, @RequestBody Topics topic){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");


        Topics topicToCreate = topicsService.addTopics(topic, token);
        Map<String, Object> response = new HashMap<>();

        return null;
    }
}
