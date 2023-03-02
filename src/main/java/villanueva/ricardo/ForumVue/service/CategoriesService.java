package villanueva.ricardo.ForumVue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.ForumVue.dao.CategoriesDAO;
import villanueva.ricardo.ForumVue.model.Categories;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    CategoriesDAO categoriesDao;

    public List<Categories> findByTitle(String title) {
        return categoriesDao.findByTitleLike(title);
    }

    public void addCategory(Categories category) {
        categoriesDao.save(category);
    }
}
