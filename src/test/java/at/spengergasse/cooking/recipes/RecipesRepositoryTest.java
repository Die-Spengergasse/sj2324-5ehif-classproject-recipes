package at.spengergasse.cooking.recipes;

import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.domain.utils.Key;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.*;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataMongoTest
@ContextConfiguration(classes = TestRecipesApplication.class)
public class RecipesRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setup(){
        assumeThat(recipeRepository).isNotNull();
    }

    @Test
    void writeReadTest(){
        Recipe recipe = Recipe.builder()
                .author(new User(Key.randomKey().toString(), "Thomas"))
                .description("A test recipe")
                .categories(Arrays.asList(new Category("Test", null)))
                .likes(5)
                .key(Key.randomKey().toString())
                .difficulty(Difficulty.BEGINNER)
                .creationTS(ZonedDateTime.of(LocalDate.of(2023, 11, 14),
                        LocalTime.of(9, 26),
                        ZoneId.of("UTC")))
                .comments(Arrays.asList(
                        new Comment(new User(Key.randomKey().toString(), "Billy Bob"), "Very Cool Test", null,
                                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                                        LocalTime.of(12, 32),
                                        ZoneId.of("America/Vancouver")))))
                .ingredients(Arrays.asList(new Ingredient("Pasta", Key.randomKey().toString(), 100, Unit.GRAMS)))
                .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
                .titlePictureID("picture123")
                .build();

        var saved = recipeRepository.save(recipe);

        assertThat(saved).isSameAs(recipe);
        assertThat(saved.toString()).isNotNull();
    }

}
