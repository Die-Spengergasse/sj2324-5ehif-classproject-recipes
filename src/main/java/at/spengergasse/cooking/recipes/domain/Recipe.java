package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("recipes")
@AllArgsConstructor
public class Recipe {
    @Id
    private String key;
    public String username;
    public String userKey;

}
