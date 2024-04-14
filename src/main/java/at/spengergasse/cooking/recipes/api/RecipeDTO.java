package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.service.user.UserDto;

import java.time.ZonedDateTime;
import java.util.List;

public record RecipeDTO(
        String key,
        String autorUsername,
        String title,
        String description,
        List<IngredientDTO> ingredientDTOList,
        ZonedDateTime createionTS,
        int likes,
        NutrientSummary nutrientSummaryDTO,
        List<Category> categories,
        List<Comment> commentDTOList,
        Difficulty difficulty,
        String titlePictureID
) {
}
