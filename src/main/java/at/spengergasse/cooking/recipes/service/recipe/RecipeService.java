package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.image.ImageService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
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
    private final ImageService imageService;

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
                    .ingredients(cmd.ingredients().stream().map(ingredient -> KeyType.parse(ingredient).ensureValid(KeyType.INGREDIENT)).collect(Collectors.toList()))
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

    /*

    public Recipe updateLikes(Recipe existingRecipe, UpdateLikesCommand updateLikesCommand) {
        Recipe updatedRecipe = existingRecipe.toBuilder()
                .likes(updateLikesCommand.likes())
                .build();

        return recipeRepository.save(updatedRecipe);
    }*/

    public Optional<RecipeDto> likeRecipe(Key key) {
        final Optional<Recipe> recipe = this.recipeRepository.findById(key);

        return recipe.map(found -> {
            found.setLikes(found.getLikes() + 1);

            this.recipeRepository.save(found);

            return new RecipeDto(found);
        });
    }

    public Optional<RecipeDto> dislikeRecipe(Key key) {
        final Optional<Recipe> recipe = this.recipeRepository.findById(key);

        return recipe.map(found -> {
            found.setLikes(Math.max(found.getLikes() - 1, 0));

            this.recipeRepository.save(found);

            return new RecipeDto(found);
        });
    }

    public List<RecipeDto> findRecipes() {
        // TODO: Paging!!!
        return this.recipeRepository.findAll().stream().map(RecipeDto::new).collect(Collectors.toList());
    }

    public Optional<RecipeDto> findById(Key id) {
        return this.recipeRepository.findById(id).map(RecipeDto::new);
    }

    public void deleteById(Key id) {
        this.recipeRepository.deleteById(id);
    }

    public List<Recipe> findWithQuery(Query query){
        // TODO: DTO!
        return recipeRepository.findAllBy(query);
    }
}
