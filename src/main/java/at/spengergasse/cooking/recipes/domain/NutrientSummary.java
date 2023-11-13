package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.constraints.Min;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NutrientSummary {
    @Min(0)
    public int kcal;
    @Min(0)
    public double carbs;
    @Min(0)
    public double fats;
    @Min(0)
    public double protein;
}
