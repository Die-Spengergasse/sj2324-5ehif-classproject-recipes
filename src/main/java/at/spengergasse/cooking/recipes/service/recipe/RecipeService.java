package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RecipeService {
    private final UserClient userService;
    private final RecipeRepository recipeRepository;

    // TODO: Proper recipe dto!

    public Recipe createRecipe(CreateRecipeCommand cmd){
        // TODO: DO not block!
        final UserDto user = this.userService.getUser(KeyType.parse(cmd.authorKey()).ensureValid(KeyType.USER));

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

    public List<Recipe> findRecipes() {
        return this.recipeRepository.findRecipes();
    }

    public Optional<Recipe> findById(Key id) {
        return this.recipeRepository.findById(id);
    }

    public void deleteById(Key id) {
        this.recipeRepository.deleteById(id);
    }
}
