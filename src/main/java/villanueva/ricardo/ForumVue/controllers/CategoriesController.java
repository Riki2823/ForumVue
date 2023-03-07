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

import java.util.*;

@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/categories")
    @CrossOrigin(origins = {"http://localhost:3000"})
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
    @CrossOrigin(origins = {"http://localhost:3000"})
    public Map<String, Object> returnCategory(@PathVariable String catSlug){
        return categoriesService.responseBuilder(catSlug);
    }




    @GetMapping("/categories")
    @CrossOrigin(origins = {"http://localhost:3000"})
    public List<Categories> getCategories(){
        return categoriesService.getAllCategories();
    }

    @DeleteMapping("/categories/{catSlug}")
    @CrossOrigin( origins = {"http://localhost:3000"})
    public boolean deleteCategorie(@PathVariable String catSlug,  HttpServletResponse resp){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        if(tokenService.isAdmin(token)){
            categoriesService.deleteCatcegoryBySlug(catSlug);
            return true;
        }else {
            resp.setStatus(401);
            return false;
        }

    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @PutMapping("/categories/{catSlug}")
    public Map<String, Object> updateCategory(HttpServletResponse resp, @RequestBody Categories categories, @PathVariable String catSlug){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        Map<String, Object> response = new HashMap<>();

        if( tokenService.isAdmin(token)){


            Categories category = new Categories();
            BeanUtils.copyProperties(categories, category);


            if (categoriesService.exists(catSlug)) {
                categoriesService.updateCategory(category, catSlug);
                response = categoriesService.responseBuilder(category.getSlug());

            }else{
                response.put("message", "La categoria que intentas modificar no existe");
            }

        }else {
            resp.setStatus(401);
        }
        return response;
    }


}
