package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@RequiredArgsConstructor
@Document("recipes")
@AllArgsConstructor
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // todo: check if private would work
public class Recipe {
    @Id
    @NonNull
    public String key;
    @NonNull
    public User author;
    @NonNull
    @NotBlank(message = "Title must not be blank")
    public String title;
    @NonNull
    @NotBlank(message = "Description must not be blank")
    public String description;
    @Size(min = 1)
    public List<Ingredient> ingredients;
    public ZonedDateTime creationTS;
    @Min(0)
    public int likes;
    public NutrientSummary nutrientSummary;
    public List<Category> categories;
    public List<Comment> comments;
    public Difficulty difficulty;
    public String titlePictureID;
}
