package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.TestRecipesApplication;
import at.spengergasse.cooking.recipes.api.RecipeController;
import at.spengergasse.cooking.recipes.domain.*;
import at.spengergasse.cooking.recipes.domain.utils.Key;
import at.spengergasse.cooking.recipes.persistance.RecipeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(classes = TestRecipesApplication.class)
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @Test
    void testGetAllRecipes() throws Exception {
        var request = get("/api/recipes/").accept(MediaType.APPLICATION_JSON);

        when(recipeRepository.findRecipes()).thenReturn(List.of(Recipe.builder()
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
                .build()));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].author.username").value("Thomas"))
                .andExpect(jsonPath("$[0].title").doesNotExist())
                .andExpect(jsonPath("$[0].description").value("A test recipe"))
                .andExpect(jsonPath("$[0].likes").value(5))
                .andExpect(jsonPath("$[0].difficulty").value("BEGINNER"))
                .andExpect(jsonPath("$[0].creationTS").exists())
                .andExpect(jsonPath("$[0].comments[0].author.username").value("Billy Bob"))
                .andExpect(jsonPath("$[0].ingredients[0].name").value("Pasta"))
                .andExpect(jsonPath("$[0].nutrientSummary.kcal").value(10))
                .andExpect(jsonPath("$[0].titlePictureID").value("picture123"));
    }
}