package at.spengergasse.cooking.recipes.api;

        import at.spengergasse.cooking.recipes.TestRecipesApplication;
        import at.spengergasse.cooking.recipes.api.RecipeController;
        import at.spengergasse.cooking.recipes.domain.*;
        import at.spengergasse.cooking.recipes.domain.utils.Key;
        import at.spengergasse.cooking.recipes.persistance.RecipeRepository;

        import at.spengergasse.cooking.recipes.service.RecipeService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.*;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.MediaType;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

        import static org.assertj.core.api.Assumptions.assumeThat;
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
    private RecipeService recipeService;

    @BeforeEach
    void setup(){
        assumeThat(recipeService).isNotNull();
        assumeThat(mockMvc).isNotNull();
    }

    @Test
    void testGetAllRecipes() throws Exception {
        var request = get("/api/recipes/").accept(MediaType.APPLICATION_JSON);

        when(recipeService.getAllRecipes()).thenReturn(List.of(Recipe.builder()
                .author(new User(Key.randomKey("USR", 14).toString(), "Thomas"))
                .title("Test")
                .description("A test recipe")
                .categories(Arrays.asList(new Category("Test", null)))
                .likes(5)
                .key(Key.randomKey(Key.RECIPE_PREFIX, Key.RECIPE_LENGTH).toString())
                .difficulty(Difficulty.BEGINNER)
                .creationTS(ZonedDateTime.of(LocalDate.of(2023, 11, 14),
                        LocalTime.of(9, 26),
                        ZoneId.of("UTC")))
                .comments(List.of(
                        new Comment(new User(Key.randomKey("USR", 14).toString(), "Billy Bob"), "Very Cool Test", null,
                                ZonedDateTime.of(LocalDate.of(2023, 11, 15),
                                        LocalTime.of(12, 32),
                                        ZoneId.of("America/Vancouver")))))
                .ingredients(List.of(new Ingredient("Pasta", Key.randomKey("ING", 10).toString(), 100, Unit.GRAMS)))
                .nutrientSummary(new NutrientSummary(10, 5, 5, 5))
                .titlePictureID("picture123")
                .build()));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                });
    }

}