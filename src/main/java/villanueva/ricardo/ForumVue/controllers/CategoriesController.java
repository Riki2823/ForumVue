package villanueva.ricardo.ForumVue.controllers;

import jdk.jfr.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import villanueva.ricardo.ForumVue.model.Categories;

import java.util.List;
import java.util.Map;

@RestController
public class CategoriesController {

    @PostMapping("/categories")
    public Map<String, Object> create(@RequestBody Categories categories){
        Categories category = new Categories();
        List<Categories> categoriesList = categoriesService.findByTitle();
        BeanUtils.copyProperties(categories, category);
        if (categoriesList.size() > 0){
            String aux = category.getTitle().toLowerCase().replace(" ", "-");
            int n = categoriesList.size();


        }
    }
}
