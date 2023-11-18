package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document
@AllArgsConstructor
public class Category {
    public String name;
    public String parentCategory;
}
