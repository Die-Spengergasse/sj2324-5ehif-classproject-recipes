package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.Unit;

public record IngredientDTO(
        String name,
        double amount,
        Unit unit

) {
}
