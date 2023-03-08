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
import java.util.List;

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
        Topics toCreate = new Topics(topic.getContent(), topic.getTitle(), topic.getCategorySlug());
        Categories cat = categoriesService.getCategoryBySlug(toCreate.getCategorySlug());
        toCreate.setCategory(cat);
        String date = OffsetDateTime
                .now( ZoneOffset.UTC )
                .minus( Period.ofYears( 1 ) )
                .toString();
        toCreate.setCreationDate(date);
        toCreate.setUpdatedDate(date);
        User u = userService.findById(tokenService.getUserId(token));
        toCreate.setUser(u);
        toCreate.setViews(0);
        toCreate.setnReplies(0);

        topicDAO.save(toCreate);


        return toCreate;

    }

}
