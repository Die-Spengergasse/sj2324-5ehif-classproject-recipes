package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Document("recipes")
@AllArgsConstructor
public class Recipe {
    @Id
    public String key;
    public User author;
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
}
