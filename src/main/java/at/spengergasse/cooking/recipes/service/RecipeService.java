package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.User;
import at.spengergasse.cooking.recipes.domain.utils.Key;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;
import at.spengergasse.cooking.recipes.service.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.commands.ReplaceRecipeCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(CreateRecipeCommand cmd){

        Recipe recipe = Recipe.builder()
                .title(cmd.title())
                .description(cmd.description())
                .author(new User(cmd.authorKey(), "JohnDoe"))
                .ingredients(cmd.ingredientList())
                .categories(cmd.categories())
                .difficulty(cmd.difficulty())
                .titlePictureID(cmd.titlePictureID())
                .creationTS(ZonedDateTime.now())
                .likes(0)
                .comments(new ArrayList<>())
                .key(Key.randomKey(Key.RECIPE_PREFIX, Key.RECIPE_LENGTH).toString())
                .build();

        recipeRepository.save(recipe);
        return recipe;
    }

    public List <Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(String key) {
        return Optional.ofNullable(recipeRepository.findRecipeByKey(key));
    }

    public void replace(String key, ReplaceRecipeCommand cmd) {
        Optional<Recipe> existingRecipe = getRecipe(key);
        if (existingRecipe.isPresent()) {
            Recipe recipe = existingRecipe.get();
            recipe.setTitle(cmd.title());
            recipe.setDescription(cmd.description());
            recipe.setAuthor(new User(cmd.authorKey(), cmd.username()));
            recipe.setIngredients(cmd.ingredientList());
            recipe.setCategories(cmd.categories());
            recipe.setDifficulty(cmd.difficulty());
            recipe.setTitlePictureID(cmd.titlePictureID());
            recipeRepository.save(recipe);
        }
    }
}
