package villanueva.ricardo.ForumVue.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {


    @GetMapping("/categories/{catSlug}/topics")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public String[] returnTopics(@PathVariable String catSlug){
        String[] response = new String[0];
        return response;
    }

    @PostMapping("/categories/topics")
    @CrossOrigin("/")
}
