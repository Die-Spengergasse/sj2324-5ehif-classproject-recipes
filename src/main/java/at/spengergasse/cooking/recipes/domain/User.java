package at.spengergasse.cooking.recipes.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    public String userKey;
    public String username;
    public String profilePictureID;
}
