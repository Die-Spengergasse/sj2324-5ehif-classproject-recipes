package at.spengergasse.cooking.recipes.service;

import at.spengergasse.cooking.recipes.domain.utils.search.FilterCondition;
import at.spengergasse.cooking.recipes.domain.utils.search.FilterOperation;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilterBuilderService {

    public List<FilterCondition> createFilterCondition(String criteria){
        List<FilterCondition> filters = new ArrayList<>();

        try{
            if(criteria != null && !criteria.isEmpty()){
                final String FILTER_SEARCH_DELIMITER = "&";
                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(criteria, FILTER_SEARCH_DELIMITER);
                if(!values.isEmpty()){
                    values.forEach(x -> {
                        List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                        if(FilterOperation.fromValue(filter.get(1)) != null){
                            filters.add(new FilterCondition(filter.get(0), FilterOperation.fromValue(filter.get(1)), filter.get(2)));
                        }
                    });
                }
            }

            return filters;
        }
        catch (Exception exception){
            //throw new BadRequestException("Cannot create filter " + exception.getMessage());
            return null;
        }
    }

    private static List<String> split(String search, String delimiter){
        return Stream.of(search.split(delimiter)).collect(Collectors.toList());
    }
}
