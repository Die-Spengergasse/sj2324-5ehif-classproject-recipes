package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.Key;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;
import at.spengergasse.cooking.recipes.service.commands.CreateRecipeCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@AllArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(CreateRecipeCommand cmd){

        Recipe recipe = Recipe.builder()
                .title(cmd.title())
                .description(cmd.description())
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
}
