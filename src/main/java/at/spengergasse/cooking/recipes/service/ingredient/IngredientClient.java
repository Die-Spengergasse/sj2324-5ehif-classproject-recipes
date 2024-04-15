package at.spengergasse.cooking.recipes.service.ingredient;

import at.spengergasse.cooking.recipes.domain.NutrientSummary;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import at.spengergasse.cooking.recipes.service.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ingredients", url = "${recipe.ingredients.endpoint}")
public interface IngredientClient {
    @RequestMapping(method = RequestMethod.GET, value = "/Ingredients/getById/{id}")
    public IngredientDto getIngredient(@PathVariable("id") Key id);

    public default NutrientSummary calculateSummary(Key... ingredients) {
        final NutrientSummary summary = new NutrientSummary(0, 0D, 0D, 0D);

        // TODO: Make batch call for better efficiency.
        for(Key ingredientId : ingredients) {
            final var ingredient = this.getIngredient(ingredientId);

            // todo: kcal

            summary.setFats(summary.getFats() + ingredient.fatsInGram());
            summary.setCarbs(summary.getCarbs() + ingredient.carbohydratesInGram());
            summary.setProtein(summary.getProtein() + ingredient.proteinsInGram());
        }

        return summary;
    }
}
