package villanueva.ricardo.ForumVue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.model.Topics;

import java.util.List;


public interface TopicDAO extends JpaRepository<Topics, Long> {
}
