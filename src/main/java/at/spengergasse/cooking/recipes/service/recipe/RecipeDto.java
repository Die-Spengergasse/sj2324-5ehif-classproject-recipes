package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.service.user.UserDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record RecipeDto(
        String key,
        UserDto author,
        String title,
        String description,
        List<String> ingredients,
        ZonedDateTime creationTS,
        long likes,
        NutrientSummaryDto nutrientSummary,
        List<CategoryDto> categories,
        List<CommentDto> comments,
        Difficulty difficulty,
        String titlePictureID
) {
    public RecipeDto(Recipe recipe) {
        this(
                recipe.getKey() + "",
                new UserDto(recipe.getAuthor()),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getIngredients().stream().map(Key::toString).collect(Collectors.toList()),
                recipe.getCreationTS(),
                recipe.getLikes(),
                recipe.getNutrientSummary() != null ? new NutrientSummaryDto(recipe.getNutrientSummary()) : null,
                recipe.getCategories().stream().map(CategoryDto::new).collect(Collectors.toList()),
                recipe.getComments().stream().map(CommentDto::new).collect(Collectors.toList()),
                recipe.getDifficulty(),
                recipe.getTitlePictureID()
        );
    }
}
