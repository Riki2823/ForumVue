package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import villanueva.ricardo.ForumVue.model.Topics;
import villanueva.ricardo.ForumVue.service.CategoriesService;
import villanueva.ricardo.ForumVue.service.TokenService;
import villanueva.ricardo.ForumVue.service.TopicsService;

import java.util.HashMap;
import java.util.List;
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
    public Map<Integer, Object> returnTopics(@PathVariable String catSlug){
        List<Topics> topics = topicsService.getBySlug(catSlug);
        Map<Integer, Object> response = new HashMap<>();
        int aux = 0;
        for (Topics t: topics){
            t.set_id();
            response.put(aux, t);
            aux++;
        }
        return response;
    }

    @PostMapping("/topics")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public Map<String, Object> createTopic(HttpServletResponse resp, @RequestBody Topics topic){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");


        Topics topicToCreate = topicsService.addTopics(topic, token);
        Map<String, Object> response = new HashMap<>();
        response.put("category", topicToCreate.getCategory());
        response.put("content", topicToCreate.getContent());
        response.put("createdAt", topicToCreate.getCreatedAt());
        response.put("id", topicToCreate.getId());
        response.put("numberOfReplies", topicToCreate.getReplies());
        response.put("replies", null);
        response.put("title", topicToCreate.getTitle());
        response.put("updatedAt", topicToCreate.getUpdatedDate());
        response.put("user", topicToCreate.getUser().getId());
        response.put("views", topicToCreate.getViews());
        response.put("__v", 0);
        response.put("_id", topicToCreate.get_id());


        return response;
    }

    @GetMapping("/topics/{topicID}")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public Map<String, Object> getTopic(@PathVariable String topicID, HttpServletResponse resp){
        Topics t = topicsService.getTopicById(topicID);
        if (t == null){
            resp.setStatus(404);
        }
        Map<String, Object> response = topicsService.buildRespGet(t);
        return response;

    }
}
