package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
@Getter
@RequiredArgsConstructor
public record RecipeDTO(
        UserDto userDTO,
        String title,
        String description,
        List<IngredientDTO> ingredientDTOList,
        ZonedDateTime creationTS,
        int likes,
        NutrientSummaryDTO nutrientSummaryDTO,
        List<Category> categories,
        List<CommentDTO> commentDTOList,
        Difficulty difficulty,
        String titlePictureID
) {
}
