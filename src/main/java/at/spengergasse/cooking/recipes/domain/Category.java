package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document
@AllArgsConstructor
@Getter
@Setter
public class Category {
    private String name;
    private String parentCategory;
}
