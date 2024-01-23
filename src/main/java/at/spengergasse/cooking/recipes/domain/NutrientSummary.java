package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Getter
@Setter
public class NutrientSummary {
    @Min(0)
    private int kcal;
    @Min(0)
    private double carbs;
    @Min(0)
    private double fats;
    @Min(0)
    private double protein;
}
