package at.spengergasse.cooking.recipes.domain.utils.search;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterCriteriaBuilder {

    private final List<FilterCondition> filterAndConditions;
    private final List<FilterCondition> filterOrConditions;

    private static final Map<String, Function<FilterCondition, Criteria>> FILTER_CRITERIA = new HashMap<>();

    static{
        FILTER_CRITERIA.put("EQUAL", condition -> Criteria.where(condition.getField()).is(condition.getValue()));
        FILTER_CRITERIA.put("NOT_EQUAL", condition -> Criteria.where(condition.getField()).ne(condition.getValue()));
        FILTER_CRITERIA.put("CONTAINS", condition -> Criteria.where(condition.getField()).regex((String) condition.getValue()));
    }

    public FilterCriteriaBuilder(){
        filterOrConditions = new ArrayList<>();
        filterAndConditions = new ArrayList<>();
    }

    public Query addCondition(List<FilterCondition> andConditions, List<FilterCondition> orConditions){
        if (andConditions != null && !andConditions.isEmpty()){
            filterAndConditions.addAll(andConditions);
        }
        if (orConditions != null && !orConditions.isEmpty()){
            filterOrConditions.addAll(orConditions);
        }

        List<Criteria> criteriaAndClause = new ArrayList<>();
        List<Criteria> criteriaOrClause = new ArrayList<>();

        Criteria criteria = new Criteria();

        filterAndConditions.stream().map(condition -> criteriaAndClause.add(buildCriteria(condition))).collect(Collectors.toList());
        filterOrConditions.stream().map(condition -> criteriaOrClause.add(buildCriteria(condition))).collect(Collectors.toList());

        if (!criteriaAndClause.isEmpty() && !criteriaOrClause.isEmpty()){
            return new Query(criteria.andOperator(criteriaAndClause.toArray(new Criteria[0])).orOperator(criteriaOrClause.toArray(new Criteria[0])));
        }
        else if (!criteriaAndClause.isEmpty()){
            return new Query(criteria.andOperator(criteriaAndClause.toArray(new Criteria[0])));
        }
        else if (!criteriaOrClause.isEmpty()){
            return new Query(criteria.orOperator(criteriaOrClause.toArray(new Criteria[0])));
        }
        else{
            return new Query();
        }
    }

    public Criteria buildCriteria(FilterCondition condition){
        Function<FilterCondition, Criteria> function = FILTER_CRITERIA.get(condition.getOperation().name());

        if (function == null){
            throw new IllegalArgumentException("Illegal filter type: ");
        }

        return function.apply(condition);
    }
}
