package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import at.spengergasse.cooking.recipes.service.recipe.RecipeDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Document("recipes")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Recipe {
    @Id
    @NonNull // TODO: mixture of annotation lombok, jakarta etc. this gud?
    private Key key;
    @NonNull
    private CachedUser author;
    @NonNull
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NonNull
    @NotBlank(message = "Description must not be blank")
    private String description;
    @Size(min = 1)
    private List<Key> ingredients;
    @NonNull
    private ZonedDateTime creationTS;
    @Min(0)
    private long likes;
    @NonNull
    private NutrientSummary nutrientSummary;
    @NonNull
    private List<Category> categories;
    @NonNull
    private List<Comment> comments;
    @NonNull
    private Difficulty difficulty;
    private String titlePictureID;
}
