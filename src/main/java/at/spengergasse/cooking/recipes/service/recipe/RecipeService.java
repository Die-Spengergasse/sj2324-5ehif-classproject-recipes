package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.recipe.commands.ReplaceRecipeCommand;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RecipeService {
    private final UserService userService;
    private final RecipeRepository recipeRepository;

    public Optional<Recipe> getRecipe(String key) {
        return KeyType.parse(key).isValid(KeyType.RECIPE) ? Optional.ofNullable(recipeRepository.findRecipeByKey(key)) : Optional.empty();
    }

    public Recipe createRecipe(CreateRecipeCommand cmd){
        // TODO: DO not block!
        final UserDto user = this.userService.getUser(KeyType.parse(cmd.authorKey()).ensureValid(KeyType.USER)).block();

        if(user != null) {
            final Recipe recipe = Recipe.builder()
                    .title(cmd.title())
                    .description(cmd.description())
                    .author(new CachedUser(user))
                    .ingredients(cmd.ingredientList())
                    .categories(cmd.categories())
                    .difficulty(cmd.difficulty())
                    .titlePictureID(cmd.titlePictureID())
                    .creationTS(ZonedDateTime.now())
                    .likes(0)
                    .comments(new ArrayList<>())
                    .key(KeyType.RECIPE.randomKey())
                    .build();

            return recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Unknown user key.");
        }
    }

    public void deleteRecipe(String key){if(KeyType.parse(key).isValid(KeyType.RECIPE)) recipeRepository.deleteRecipeByKey(key);}

    public void updateRecipe(String key, ReplaceRecipeCommand cmd){
        final UserDto user = this.userService.getUser(KeyType.parse(cmd.authorKey()).ensureValid(KeyType.USER)).block();
        Optional<Recipe> existingRecipe = getRecipe(key);
        if (existingRecipe.isPresent() && user != null) {
            Recipe recipe = existingRecipe.get();
            recipe.setTitle(cmd.title());
            recipe.setDescription(cmd.description());
            recipe.setAuthor(new CachedUser(user));
            recipe.setIngredients(cmd.ingredientList());
            recipe.setCategories(cmd.categories());
            recipe.setDifficulty(cmd.difficulty());
            recipe.setTitlePictureID(cmd.titlePictureID());
            recipe.setCreationTS(ZonedDateTime.now());
            recipe.setLikes(0);
            recipe.setComments(new ArrayList<>());
            recipe.setKey(KeyType.RECIPE.randomKey());
            recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Cannot update recipe. Recipe with key " + key + " does not exist.");
        }
    }


}
