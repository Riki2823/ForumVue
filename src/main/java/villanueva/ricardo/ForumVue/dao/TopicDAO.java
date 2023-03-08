package villanueva.ricardo.ForumVue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import villanueva.ricardo.ForumVue.model.Topics;

import java.util.List;


public interface TopicDAO extends JpaRepository<Topics, Long> {
    List<Topics> findByCategoryLike(String catSlug);

    List<Topics> findByIdLike(long parseLong);
}
