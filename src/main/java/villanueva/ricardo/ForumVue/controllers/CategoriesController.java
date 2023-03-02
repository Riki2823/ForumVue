package villanueva.ricardo.ForumVue.controllers;

import jdk.jfr.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.service.CategoriesService;

import java.util.List;
import java.util.Map;

@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping("/categories")
    @CrossOrigin(origins = {"http://localhost:3000")
    public Map<String, Object> create(@RequestBody Categories categories){
        Categories category = new Categories();
        List<Categories> categoriesList = categoriesService.findByTitle(categories.getTitle());
        BeanUtils.copyProperties(categories, category);
        if (categoriesList.size() > 0){
            String aux = category.getTitle().toLowerCase().replace(" ", "-");
            int n = categoriesList.size();
            aux = aux + Integer.toString(n);
            category.setSlug(aux);
        }
        categoriesService.addCategory(category);
        return null;
    }
}
