package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.recipe.commands.ReplaceRecipeCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public HttpEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = recipeService.getAllRecipes();

        return ResponseEntity.ok().body(allRecipes);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Optional<Recipe>> getRecipeByKey(@PathVariable String key) {
        Optional<Recipe> recipe = recipeService.getRecipe(key);
        if(recipe == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping("/")
    public HttpEntity<Recipe> createRecipe(@RequestBody @Valid CreateRecipeCommand cmd) {
        Recipe recipe = recipeService.createRecipe(cmd);

        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("/{key}")
    public HttpEntity<Recipe> deletRecipe(@PathVariable String key) {
        recipeService.deleteRecipe(key);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{key}")
    public ResponseEntity<?> replaceReceipt(@PathVariable String key, @Validated @RequestBody ReplaceRecipeCommand cmd) {
        recipeService.updateRecipe(key, cmd);
        return ResponseEntity.noContent().build();
    }

}
