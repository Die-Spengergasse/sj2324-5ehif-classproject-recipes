package at.spengergasse.cooking.recipes.service.recipe;

import at.spengergasse.cooking.recipes.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryDto(
        List<String> categoryTree
) {
    public CategoryDto(Category category) {
        this(CategoryDto.treeCategoryHierarchy(category));
    }

    // TODO: check transactions
    private static List<String> treeCategoryHierarchy(Category category) {
        final List<Category> processed = new ArrayList<>();

        do {
            if(processed.contains(category)) break; // TODO: Recursion detected; create log message

            processed.add(category);
        } while((category = category.getParentCategory()) != null);

        return processed.stream().map(Category::getName).collect(Collectors.toList());
    }
}
