package at.spengergasse.cooking.recipes.web;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/")
    public List<Recipe> getAllRecipes(){
        List<Recipe> allRecipes = recipeRepository.findRecipes();

        return allRecipes;
    }

    @PostMapping("/")
    public void createRecipe(@RequestBody Recipe recipe){
        recipeRepository.save(recipe);
    }

}
