package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.recipe.commands.UpdateLikesCommand;
import at.spengergasse.cooking.recipes.service.user.UserClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private UserClient userService;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void testCreateRecipeUnknownUser() {
        // Arrange
        String unknownAuthorKey = KeyType.USER.randomKey().toString();
        CreateRecipeCommand createRecipeCommand = new CreateRecipeCommand(
                "Recipe Title",
                "Recipe Description",
                new ArrayList<>(),
                new ArrayList<>(),
                Difficulty.BEGINNER,
                "titlePictureID",
                unknownAuthorKey
        );

        when(userService.getUser(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> recipeService.createRecipe(createRecipeCommand, null));

        // Verify that userService.getUser was called
        verify(userService, times(1)).getUser(any());
        // Verify that recipeRepository.save was not called
        verify(recipeRepository, never()).save(any());
    }
/*x
    @Test
    void testUpdateLikes() {
        // Arrange
        String existingRecipeKey = KeyType.RECIPE.randomKey().toString();
        CachedUser creator = new CachedUser(KeyType.USER.randomKey(), "Thomas");
        CachedUser commenter = new CachedUser(KeyType.USER.randomKey(), "Adrian");
        Comment comment = new Comment(commenter, "Very Cool Test", null,
                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                        LocalTime.of(12, 32),
                        ZoneId.of("America/Vancouver")));
        Recipe existingRecipe = Recipe.builder()
                .author(creator)
                .title("Adrians best Lasagna")
                .description("A test Lasagna")
                .categories(Arrays.asList(new Category("Pasta", null)))
                .likes(5)
                .key(KeyType.RECIPE.randomKey())
                .difficulty(Difficulty.BEGINNER)
                .creationTS(ZonedDateTime.of(LocalDate.of(2023, 11, 14),
                        LocalTime.of(9, 26),
                        ZoneId.of("UTC")))
                .comments(List.of(comment))
                .ingredients(List.of(new Ingredient("Pasta", KeyType.INGREDIENT.randomKey(), 100, Unit.GRAMS)))
                .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
                .titlePictureID("picture123")
                .build();

        UpdateLikesCommand updateLikesCommand = new UpdateLikesCommand(10);

        when(recipeRepository.findById(any())).thenReturn(java.util.Optional.of(existingRecipe));

        Recipe updatedRecipe = recipeService.updateLikes(existingRecipe, updateLikesCommand);

        verify(recipeRepository, times(1)).save(updatedRecipe);
    }
    */
}
