package at.spengergasse.cooking.recipes.domain;

import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Ingredient {
    public String name;
    public String key;
    public double amount;

}
