package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.CachedUser;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.persistence.RecipeRepository;
import at.spengergasse.cooking.recipes.service.image.ImageService;
import at.spengergasse.cooking.recipes.service.recipe.commands.CreateRecipeCommand;
import at.spengergasse.cooking.recipes.service.user.PreferenceDto;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import at.spengergasse.cooking.recipes.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RecipeService {
    private final UserService userService;
    private final RecipeRepository recipeRepository;

    private final ImageService imageService;

    public Recipe createRecipe(CreateRecipeCommand cmd, MultipartFile image){
        // TODO: DO not block!
        final UserDto user = this.userService.getUser(KeyType.parse(cmd.authorKey()).ensureValid(KeyType.USER)).block();

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
}
