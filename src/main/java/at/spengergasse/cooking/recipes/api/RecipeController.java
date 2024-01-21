package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.domain.utils.search.FilterCondition;
import at.spengergasse.cooking.recipes.domain.utils.search.FilterCriteriaBuilder;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.FilterBuilderService;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Adjust the origin to match your Vue.js frontend
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FilterBuilderService filterBuilderService;
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public HttpEntity<List<Recipe>> getRecipes(@RequestParam(value = "filterOr", required = false) String filterOr,
                                               @RequestParam(value = "filterAnd", required = false) String filterAnd) {

        FilterCriteriaBuilder criteriaBuilder = new FilterCriteriaBuilder();

        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

        Query query = criteriaBuilder.addCondition(andConditions, orConditions);

        List<Recipe> allRecipes = recipeService.findWithQuery(query);

        return ResponseEntity.ok().body(allRecipes);
    }

    @GetMapping("/{key}")
    public HttpEntity<Recipe> getRecipeByKey(@PathVariable String key) {
        final Optional<Recipe> recipe = this.recipeService.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        return recipe.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/withImage")
    public HttpEntity<Recipe> createRecipeWithImage(@RequestPart("image") MultipartFile image,
                                                    @RequestPart("recipe") @Valid CreateRecipeCommand cmd) {

        Recipe recipe = recipeService.createRecipe(cmd, image);

        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping
    public HttpEntity<Recipe> createRecipe(@RequestBody @Valid CreateRecipeCommand cmd) {
        Recipe recipe = recipeService.createRecipe(cmd, null);

        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("/{key}")
    public HttpEntity<Recipe> deletRecipe(@PathVariable String key) {
        final Key parsed = KeyType.parse(key).ensureValid(KeyType.RECIPE);

        this.recipeService.deleteById(parsed);

        return ResponseEntity.ok().build();

    }

    // TODO: Exception handler
}
