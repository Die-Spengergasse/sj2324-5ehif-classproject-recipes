package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class RecipesTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testValidRecipe() {
       // Create a couple needed Objects for Recipe
        CachedUser creator = new CachedUser(KeyType.USER.randomKey(), "Thomas");
        CachedUser commenter = new CachedUser(KeyType.USER.randomKey(), "Adrian");
        Comment comment = new Comment(commenter, "Very Cool Test", null,
                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                        LocalTime.of(12, 32),
                        ZoneId.of("America/Vancouver")));
        // Create a valid Recipe instance
        Recipe recipe = Recipe.builder()
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
            .ingredients(List.of())// TODO FIX
            .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
            .titlePictureID("picture123")
            .build();

        // Validate the Recipe instance
        var violations = validator.validate(recipe);
        assertTrue(violations.isEmpty(), "Recipe should be valid");
    }

    @Test
    void testInvalidRecipe() {
        // Create an invalid Recipe instance (e.g., missing required fields)
        CachedUser creator = new CachedUser(KeyType.USER.randomKey(), "Thomas");
        CachedUser commenter = new CachedUser(KeyType.USER.randomKey(), "Adrian");
        Comment comment = new Comment(commenter, "Very Cool Test", null,
                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                        LocalTime.of(12, 32),
                        ZoneId.of("America/Vancouver")));
        Recipe invalidRecipe = Recipe.builder()
                .author(creator)
                .title("")
                .description("")
                .categories(Arrays.asList(new Category("Pasta", null)))
                .likes(0)
                .key(KeyType.RECIPE.randomKey())
                .difficulty(Difficulty.BEGINNER)
                .creationTS(ZonedDateTime.of(LocalDate.of(2023, 11, 14),
                        LocalTime.of(9, 26),
                        ZoneId.of("UTC")))
                .comments(List.of(comment))
                .ingredients(List.of())
                .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
                .titlePictureID("picture123")
                .build();
        System.out.println(invalidRecipe.toString());

        // Validate the Recipe instance
        var violations = validator.validate(invalidRecipe);
        assertFalse(violations.isEmpty(), "Recipe should be invalid");
    }
}
