package at.spengergasse.cooking.recipes.domain;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@AllArgsConstructor
public class Ingredient {
    public String name;
    public String key;
    public double amount;
    public Unit unit;

}
