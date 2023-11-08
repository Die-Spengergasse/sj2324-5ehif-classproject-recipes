package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.constraints.NotNull;
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
    public String username;
    public String userKey;
    @Size(min = 1)
    public List<Ingredient> ingredients;
    public ZonedDateTime creationTS;
    public int likes;

}
