package at.spengergasse.cooking.recipes.domain;

import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.domain.utils.key.KeyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Getter
@Setter
public class Ingredient {
    private String name;
    private Key key;
    private double amount;
    private Unit unit;

    public Ingredient(String name, Key key, double amount, Unit unit) {
        this.name = name;
        this.key = key.ensureValid(KeyType.INGREDIENT);
        this.amount = amount;
        this.unit = unit;
    }
}
