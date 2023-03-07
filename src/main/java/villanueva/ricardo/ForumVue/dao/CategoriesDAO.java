package villanueva.ricardo.ForumVue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import villanueva.ricardo.ForumVue.model.Categories;

import java.util.List;

public interface CategoriesDAO extends JpaRepository<Categories, Long> {
    List<Categories> findByTitleLike(String title);

    List<Categories> findBySlugLike(String slug);

    boolean existsBySlug(String catSlug);

    List<Categories> findByIdLike(long id);
}
