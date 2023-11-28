package at.spengergasse.cooking.recipes.api;

import at.spengergasse.cooking.recipes.domain.NutrientSummary;
import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.persistance.NutrientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrients")
public class NutrientController {

    @Autowired
    private NutrientRepository nutrientRepository;

    @GetMapping("/")
    public ResponseEntity<List<NutrientSummary>> getAllNutrients(){
        List<NutrientSummary> allNutrients = nutrientRepository.findNutrients();

        return ResponseEntity.ok().body(allNutrients);
    }


    @PostMapping("/")
    public HttpEntity<NutrientSummary> createRecipe(@RequestBody @Valid NutrientSummary nutrientSummary){
        return ResponseEntity.ok().body(nutrientRepository.save(nutrientSummary));
    }
}
