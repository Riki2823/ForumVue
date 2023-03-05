package villanueva.ricardo.ForumVue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import villanueva.ricardo.ForumVue.model.Categories;
import villanueva.ricardo.ForumVue.service.CategoriesService;
import villanueva.ricardo.ForumVue.service.TokenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/categories")
    @CrossOrigin(origins = {"*"})
    public Map<String, Object> create(@RequestBody Categories categories, HttpServletResponse resp){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if(tokenService.isAdmin(token)){
            Categories category = new Categories();
            List<Categories> categoriesList = categoriesService.findByTitle(categories.getTitle());
            BeanUtils.copyProperties(categories, category);

            if (categoriesList.size() > 0){
                String aux = category.getTitle().toLowerCase().replace(" ", "-");
                int n = categoriesList.size();
                aux = aux + Integer.toString(n);
                category.setSlug(aux);
            }else{
                String aux = category.getTitle().toLowerCase().replace(" ", "-");
                category.setSlug(aux);
            }

            categoriesService.addCategory(category);

            return categoriesService.responseBuilder(category.getSlug());
        }else {
            resp.setStatus(401);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "No puedes crear categorias si no eres administrador");
            return response;
        }

    }

    @GetMapping("/categories/{catSlug}")
    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
    public Map<String, Object> returnCategory(@PathVariable String catSlug){
        return categoriesService.responseBuilder(catSlug);
    }


    @GetMapping("/categories/{catSlug}/topics")
    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
    public String[] returnTopics(@PathVariable String catSlug){
        String[] response = new String[0];
        return response;
    }

    @GetMapping("/categories")
    @CrossOrigin(origins = {"http://192.168.8.155:3000"})
    public List<Categories> getCategories(){
        return categoriesService.getAllCategories();
    }

    @DeleteMapping("/categories/{catSlug}")
    @CrossOrigin( origins = {"http://192.168.8.155:3000"})
    public boolean deleteCategorie(@PathVariable String catSlug){



        return true;
    }
}
