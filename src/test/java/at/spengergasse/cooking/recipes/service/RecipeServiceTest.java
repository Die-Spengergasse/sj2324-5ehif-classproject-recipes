package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.Difficulty;
import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.recipe.RecipeService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserService;
import org.junit.jupiter.api.Test;  
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private UserService userService;

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

        when(userService.getUser(any())).thenReturn(Mono.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> recipeService.createRecipe(createRecipeCommand));

        // Verify that userService.getUser was called
        verify(userService, times(1)).getUser(any());
        // Verify that recipeRepository.save was not called
        verify(recipeRepository, never()).save(any());
    }
}
