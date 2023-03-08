package villanueva.ricardo.ForumVue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.ForumVue.dao.TopicDAO;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.model.Topics;
import villanueva.ricardo.ForumVue.model.User;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicsService {
    @Autowired
    CategoriesService categoriesService;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    TopicDAO topicDAO;

    public Topics addTopics(Topics topic, String token){
        Topics toCreate = new Topics(topic.getContent(), topic.getTitle(), topic.getCategory());
        Categories cat = categoriesService.getCategoryBySlug(toCreate.getCategory());
        toCreate.setCategoryRef(cat);
        String date = OffsetDateTime
                .now( ZoneOffset.UTC )
                .minus( Period.ofDays( 1 ) )
                .toString();
        toCreate.setCreatedAt(date);
        toCreate.setUpdatedDate(date);
        User u = userService.findById(tokenService.getUserId(token));
        toCreate.setUser(u);
        toCreate.setViews(0);
        toCreate.setReplies(0);

        topicDAO.save(toCreate);
        toCreate.set_id();

        return toCreate;

    }

    public List<Topics> getBySlug(String catSlug) {
        return topicDAO.findByCategoryLike(catSlug);
    }

    public Topics getTopicById(String topicID) {
         List<Topics> topics = topicDAO.findByIdLike(Long.parseLong(topicID));
         Topics t = topics.get(0);
         t.set_id();
         return t;
    }

    public Map<String, Object> buildRespGet(Topics t) {
        Map<String, Object> response = new HashMap<>();
        t.setViews(t.getViews()+1);

        topicDAO.save(t);

        response.put("category", t.getCategoryRef());
        response.put("content", t.getContent());
        response.put("createdAt", t.getCreatedAt());
        response.put("id", t.get_id());
        response.put("numberOfReplies", null);
        response.put("replies", new String[0]);
        response.put("title", t.getTitle());
        response.put("updatedAt", t.getUpdatedDate());
        response.put("views", t.getViews());
        response.put("__v", 0);
        response.put("_id", t.get_id());

        Map<String, Object> user = new HashMap<>();
        user.put("avatarUrl", "");
        user.put("email", t.getUser().getEmail());
        user.put("id", t.getUser().getId().toString());
        user.put("name", t.getUser().getName());
        user.put("role", t.getUser().getRole());
        user.put("__v", 0);
        user.put("_id", t.getUser().getId().toString());

        response.put("user", user);

        return response;

    }
}
