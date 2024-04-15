package at.spengergasse.cooking.recipes.domain.utils.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterCondition {

    private String field;
    private FilterOperation operation;
    private Object value;
}
