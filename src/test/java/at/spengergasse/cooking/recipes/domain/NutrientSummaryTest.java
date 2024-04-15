package at.spengergasse.cooking.recipes.domain;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NutrientSummaryTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Test
    void testValidNutrientSummary() {
        NutrientSummary nutrientSummary = new NutrientSummary(350, 50.0, 10.0, 25.0);
        var violations = validator.validate(nutrientSummary);
        assertTrue(violations.isEmpty(), "NutrientSummary should be valid");
    }

    @Test
    void testInvalidNutrientSummaryWithNegativeValues() {
        NutrientSummary invalidNutrientSummary = new NutrientSummary(-10, -5.0, -2.5, -30.0);
        var violations = validator.validate(invalidNutrientSummary);
        assertFalse(violations.isEmpty(), "NutrientSummary should be invalid due to negative values");
    }

    @Test
    void testBoundaryConditions() {
        NutrientSummary edgeCaseNutrientSummary = new NutrientSummary(0, 0.0, 0.0, 0.0);
        var violations = validator.validate(edgeCaseNutrientSummary);
        assertTrue(violations.isEmpty(), "NutrientSummary with boundary values should be valid");
    }
}