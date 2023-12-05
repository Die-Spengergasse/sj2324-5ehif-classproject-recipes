package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Builder
@RequiredArgsConstructor
@Document("recipes")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Recipe {
    @Id
    @NonNull
    public Key key;
    @NonNull
    public CachedUser author;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        //return likes == recipe.likes && Objects.equals(key, recipe.key) && Objects.equals(author, recipe.author) && Objects.equals(title, recipe.title) && Objects.equals(description, recipe.description) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(creationTS, recipe.creationTS) && Objects.equals(nutrientSummary, recipe.nutrientSummary) && Objects.equals(categories, recipe.categories) && Objects.equals(comments, recipe.comments) && difficulty == recipe.difficulty && Objects.equals(titlePictureID, recipe.titlePictureID);
        return recipe.key.equals(this.key);
    }

    @Override
    public int hashCode() {
        //return Objects.hash(this.key);
        return Objects.hash(key, author, title, description, ingredients, creationTS, likes, nutrientSummary, categories, comments, difficulty, titlePictureID);
    }
}
