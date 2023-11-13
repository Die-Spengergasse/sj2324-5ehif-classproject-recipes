package at.spengergasse.cooking.recipes.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document
public class Category {
    public String name;
    public String parentCategory;
}
