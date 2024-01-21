package at.spengergasse.cooking.recipes.service.recipe.commands;

import at.spengergasse.cooking.recipes.domain.Category;
import at.spengergasse.cooking.recipes.domain.Difficulty;
import at.spengergasse.cooking.recipes.domain.Ingredient;

import java.util.List;

public record CreateRecipeCommand(
        String title,
        String description,
        List<Ingredient> ingredientList, // TODO: Use proper dto instead?
        List<Category> categories,
        Difficulty difficulty,
        String titlePictureID,
        String authorKey
) {
}
