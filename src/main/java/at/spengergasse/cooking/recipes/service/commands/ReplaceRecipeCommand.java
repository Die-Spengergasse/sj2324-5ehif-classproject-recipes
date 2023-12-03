package at.spengergasse.cooking.recipes.service.commands;

import at.spengergasse.cooking.recipes.domain.Category;
import at.spengergasse.cooking.recipes.domain.Difficulty;
import at.spengergasse.cooking.recipes.domain.Ingredient;

import java.util.List;
import java.util.Optional;

public record ReplaceRecipeCommand(
        String title,
        String description,
        List<Ingredient> ingredientList,
        List<Category> categories,
        Difficulty difficulty,
        String titlePictureID,
        String authorKey,
        String username
) {
}
