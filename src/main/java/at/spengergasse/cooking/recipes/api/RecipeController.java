package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/")
    public HttpEntity<List<Recipe>> getAllRecipes(){
        List<Recipe> allRecipes = recipeRepository.findRecipes();

        return ResponseEntity.ok().body(allRecipes);
    }

    @PostMapping("/")
    public HttpEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe){
        recipeRepository.save(recipe);

        return ResponseEntity.ok().body(recipe);
    }

}
