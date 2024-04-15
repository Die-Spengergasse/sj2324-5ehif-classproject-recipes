package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.NutrientSummary;

public record NutrientSummaryDto(
        int kcal,
        double carbs,
        double fats,
        double protein
) {
    public NutrientSummaryDto(NutrientSummary domain) {
        this(domain.getKcal(), domain.getCarbs(), domain.getFats(), domain.getProtein());
    }
}
