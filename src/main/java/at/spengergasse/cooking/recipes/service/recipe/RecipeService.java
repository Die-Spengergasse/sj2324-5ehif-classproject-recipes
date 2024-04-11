package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.api.CommentDTO;
import at.spengergasse.cooking.recipes.api.IngredientDTO;
import at.spengergasse.cooking.recipes.api.NutrientSummaryDTO;
import at.spengergasse.cooking.recipes.api.RecipeDTO;
import at.spengergasse.cooking.recipes.domain.Category;
import at.spengergasse.cooking.recipes.domain.Difficulty;
import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.image.ImageService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.recipe.commands.UpdateLikesCommand;
import at.spengergasse.cooking.recipes.service.user.PreferenceDto;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeService {
    private final UserClient userService;
    private final RecipeRepository recipeRepository;

    // TODO: Proper recipe dto!

    public Recipe createRecipe(CreateRecipeCommand cmd, MultipartFile image){
        final UserDto user = this.userService.getUser(KeyType.parse(cmd.authorKey()).ensureValid(KeyType.USER));

        String imageUrl = "";

        if(user != null) {
            if (image != null && !image.isEmpty()) {
                // Save or process the image as needed
                // For example, you can save it to a file or a database
                imageUrl = imageService.uploadImage(image);
            }

            final Recipe recipe = Recipe.builder()
                    .title(cmd.title())
                    .description(cmd.description())
                    .author(new CachedUser(user))
                    .ingredients(cmd.ingredientList())
                    .categories(cmd.categories())
                    .difficulty(cmd.difficulty())
                    .titlePictureID(imageUrl)
                    .creationTS(ZonedDateTime.now())
                    .likes(0)
                    .comments(new ArrayList<>())
                    .key(KeyType.RECIPE.randomKey())
                    .build();

            return recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("Unknown user key.");
        }
    }

    private final ImageService imageService;

    public Recipe updateLikes(Recipe existingRecipe, UpdateLikesCommand updateLikesCommand) {
        Recipe updatedRecipe = existingRecipe.toBuilder()
                .likes(updateLikesCommand.likes())
                .build();

        return recipeRepository.save(updatedRecipe);
    }

    public List<Recipe> findRecipes() {

        List<Recipe> allRecipes = this.recipeRepository.findRecipes();
        return allRecipes;
        //ToDo Implement right DTO
        /*
        List<RecipeDTO> RecipesDTOS = allRecipes.stream().map(r -> new RecipeDTO(
                r.getAuthor(),
                r.getTitle(),
                r.getDescription(),
                r.getIngredients(),
                r.getCreationTS(),
                r.getLikes(),
                r.getNutrientSummary(),
                r.getCategories(),
                r.getComments(),
                r.getDifficulty(),
                r.getTitlePictureID()))
                .collect(Collectors.toList());
        return RecipesDTOS;
        */
    }

    public Optional<Recipe> findById(Key id) {
        return this.recipeRepository.findById(id);
    }

    public void deleteById(Key id) {
        this.recipeRepository.deleteById(id);
    }
}
