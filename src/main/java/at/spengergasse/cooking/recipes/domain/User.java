package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
public class User {
    public String userKey;
    public String username;
}
