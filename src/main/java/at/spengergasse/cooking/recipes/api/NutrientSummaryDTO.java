package at.spengergasse.cooking.recipes.api;

public record NutrientSummaryDTO(
        int kcal,
        double carbs,
        double fats,
        double protein
) {

}
