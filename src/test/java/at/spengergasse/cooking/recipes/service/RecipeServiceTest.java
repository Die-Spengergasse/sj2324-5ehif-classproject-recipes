package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
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

import static org.junit.Assert.*;
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

    private Recipe buildFunctionalRecipeObject() {
        Key recipeKey = KeyType.RECIPE.randomKey();
        CachedUser creator = new CachedUser(KeyType.USER.randomKey(), "Thomas");
        CachedUser commenter = new CachedUser(KeyType.USER.randomKey(), "Adrian");
        Comment comment = new Comment(commenter, "Very Cool Test", null,
                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                        LocalTime.of(12, 32),
                        ZoneId.of("America/Vancouver")));
        Recipe recipe = Recipe.builder()
                .author(creator)
                .title("Adrians best Lasagna")
                .description("A test Lasagna")
                .categories(Arrays.asList(new Category("Pasta", null)))
                .likes(5)
                .key(recipeKey)
                .difficulty(Difficulty.BEGINNER)
                .creationTS(ZonedDateTime.of(LocalDate.of(2023, 11, 14),
                        LocalTime.of(9, 26),
                        ZoneId.of("UTC")))
                .comments(List.of(comment))
                .ingredients(List.of(new Ingredient("Pasta", KeyType.INGREDIENT.randomKey(), 100, Unit.GRAMS)))
                .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
                .titlePictureID("picture123")
                .build();
        
        return recipe;
    }

    @Test
    void testCreateRecipeUnknownUser() {
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

        when(userService.getUser(any())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> recipeService.createRecipe(createRecipeCommand, null));

        verify(userService, times(1)).getUser(any());
        verify(recipeRepository, never()).save(any());
    }


    @Test
    public void testFindById_WhenRecipeExists() {
        Recipe recipe = buildFunctionalRecipeObject();
        Key recipeKey = recipe.getKey();

        when(recipeRepository.findById(recipe.getKey())).thenReturn(Optional.of(recipe));

        Optional<Recipe> foundRecipe = recipeService.findById(recipeKey);

        assertTrue(foundRecipe.isPresent());
        assertEquals(recipe, foundRecipe.get());
        verify(recipeRepository, times(1)).findById(recipeKey);
    }

    @Test
    public void testFindRecipes_WhenRecipesExist() {
        List<Recipe> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(buildFunctionalRecipeObject());
        expectedRecipes.add(buildFunctionalRecipeObject());
        when(recipeRepository.findRecipes()).thenReturn(expectedRecipes);

        List<Recipe> foundRecipes = recipeService.findRecipes();

        assertNotNull(foundRecipes);
        assertEquals(expectedRecipes.size(), foundRecipes.size());
        for (int i = 0; i < expectedRecipes.size(); i++) {
            assertEquals(expectedRecipes.get(i), foundRecipes.get(i));
        }
        verify(recipeRepository, times(1)).findRecipes();
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
