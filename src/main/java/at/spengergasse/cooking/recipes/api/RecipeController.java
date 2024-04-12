package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.recipe.commands.UpdateLikesCommand;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@CrossOrigin(origins = "http://localhost:8081") // Adjust the origin to match your Vue.js frontend
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public HttpEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = this.recipeService.findRecipes();
        return ResponseEntity.ok().body(allRecipes);
    }

    @GetMapping("/{key}")
    public HttpEntity<Recipe> getRecipeByKey(@PathVariable String key) {
        final Optional<Recipe> recipe = this.recipeService.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        return recipe.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/withImage")
    public HttpEntity<Recipe> createRecipeWithImage(@RequestPart("image") MultipartFile image,
                                                    @RequestPart("recipe") @Valid CreateRecipeCommand createRecipeCommand) {

        Recipe recipe = recipeService.createRecipe(createRecipeCommand, image);

        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping
    public HttpEntity<Recipe> createRecipe(@RequestBody @Valid CreateRecipeCommand createRecipeCommand) {
        log.info("Created Recipe with Data: "+createRecipeCommand);
        Recipe recipe = recipeService.createRecipe(createRecipeCommand, null);

        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping("/{key}/like")
    public HttpEntity<Recipe> like(@PathVariable String key) {
        final Optional<Recipe> existingRecipe = this.recipeService.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        if (existingRecipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Recipe updatedRecipe = recipeService.likeRecipe(existingRecipe.get());

        return ResponseEntity.ok().body(updatedRecipe);
    }

    @PostMapping("/{key}/dislike")
    public HttpEntity<Recipe> dislike(@PathVariable String key) {
        final Optional<Recipe> existingRecipe = this.recipeService.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        if (existingRecipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Recipe updatedRecipe = recipeService.dislikeRecipe(existingRecipe.get());

        return ResponseEntity.ok().body(updatedRecipe);
    }

    @PutMapping("/{key}")
    public HttpEntity<Recipe> updateRecipe(@PathVariable String key, @RequestBody @Valid UpdateLikesCommand updateLikesCommand) {
        log.info("Updated Recipe with Key: "+key+"\n With Data: "+ updateLikesCommand);
        final Optional<Recipe> existingRecipe = this.recipeService.findById(KeyType.parse(key).ensureValid(KeyType.RECIPE));

        if (existingRecipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Recipe updatedRecipe = recipeService.updateLikes(existingRecipe.get(), updateLikesCommand);

        return ResponseEntity.ok().body(updatedRecipe);
    }

    @DeleteMapping("/{key}")
    public HttpEntity<Recipe> deletRecipe(@PathVariable String key) {
        log.info("Deleted Recipe with Key: "+key);
        final Key parsed = KeyType.parse(key).ensureValid(KeyType.RECIPE);

        this.recipeService.deleteById(parsed);

        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(SerialException.class)
    public ResponseEntity<?> handleSerialException(SerialException ex) {
        log.warn("A SerialException has occurred: ", ex);
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("An Illegal Argument exception has occurred: ", ex);
        return ResponseEntity.badRequest().body("Invalid Argument: " + ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.warn("An Exception has occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
