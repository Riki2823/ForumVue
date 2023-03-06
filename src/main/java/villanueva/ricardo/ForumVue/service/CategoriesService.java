package villanueva.ricardo.ForumVue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import villanueva.ricardo.ForumVue.dao.CategoriesDAO;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Categories getCategoryBySlug(String slug) {
        return categoriesDao.findBySlugLike(slug).get(0);
    }


    public Map<String, Object> responseBuilder(String catSlug) {
        Categories responseC = getCategoryBySlug(catSlug);

        String[] moderators = new String[0];
        Map<String, Object> response = new HashMap<>();
        response.put("moderators", moderators);
        response.put("__v", 0);
        response.put("_id", responseC.getId());
        response.put("title", responseC.getTitle());
        response.put("description", responseC.getDescription());
        response.put("color", "hsl(5, 50%, 50%)");
        response.put("slug", responseC.getSlug());

        return response;
    }

    public List<Categories> getAllCategories() {
        return categoriesDao.findAll();
    }

    public Map<String, Object> getCategoriesModerates(User u) {
        Map<String, Object> categories = new HashMap<>();

        if (u.getRole().equals("user")){
            return categories;
        }else if(u.getRole().equals("admin")){
            List<Categories> categoriesList = categoriesDao.findAll();
            List<String> aux = new ArrayList<>();
            aux.add("categories_topics:write");
            aux.add("categories_topics:delete");
            aux.add("categories_replies:write");
            aux.add("categories_replies:delete");
            for (Categories c : categoriesList){
                categories.put(c.getSlug(), aux);
            }
        }
        return categories;
    }

    public void deleteCatcegoryBySlug(String catSlug) {
        Categories category = getCategoryBySlug(catSlug);
        categoriesDao.delete(category);
    }

    public void updateCategory(Categories category, String catSlug) {
        Categories aux = getCategoryBySlug(catSlug);
        category.setSlug(aux.getSlug());
        category.setId(aux.getId());
        categoriesDao.save(category);
    }

    public boolean exists(String catSlug) {
        boolean r = categoriesDao.existsBySlug(catSlug);
        return r;
    }
}
