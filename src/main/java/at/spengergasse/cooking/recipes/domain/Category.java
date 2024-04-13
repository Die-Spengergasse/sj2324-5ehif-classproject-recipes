package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Category {
    @Id
    private String name;
    @DBRef
    private Category parentCategory;
}
