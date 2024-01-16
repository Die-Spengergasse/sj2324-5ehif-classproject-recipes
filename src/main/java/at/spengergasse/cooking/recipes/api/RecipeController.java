package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Adjust the origin to match your Vue.js frontend
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public HttpEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = recipeRepository.findRecipes();

        return ResponseEntity.ok().body(allRecipes);
    }

    @GetMapping("/{key}")
    public HttpEntity<Recipe> getRecipeByKey(@PathVariable String key) {
        final Optional<Recipe> recipe = recipeRepository.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        return recipe.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public HttpEntity<Recipe> createRecipe(@RequestBody @Valid CreateRecipeCommand cmd) {
        Recipe recipe = recipeService.createRecipe(cmd);

        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("/{key}")
    public HttpEntity<Recipe> deletRecipe(@PathVariable String key) {
        final Key parsed = KeyType.parse(key).ensureValid(KeyType.RECIPE);

        recipeRepository.deleteById(parsed);

        return ResponseEntity.ok().build();

    }

}
