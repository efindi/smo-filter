package com.efindi.smo.filter;

import com.efindi.smo.exception.InvalidODataFormatException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.efindi.smo.filter.BinaryOperatorKind.AND;
import static com.efindi.smo.filter.BinaryOperatorKind.OR;
import static com.efindi.smo.filter.Constant.EXPRESSION_BRIDGE_SET;
import static com.efindi.smo.filter.Constant.EXPRESSION_SIZE;
import static com.efindi.smo.util.DateUtils.ISO8601DateTimeParser;
import static com.efindi.smo.util.MongoUtils.create$andCriteria;
import static com.efindi.smo.util.MongoUtils.create$orCriteria;
import static com.efindi.smo.util.ODataStringUtils.*;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.createNumber;
import static org.springframework.util.StringUtils.hasText;

/**
 * Filter string must be validated by ODataQueryOptionValidator prior to object creation.
 */
public class Filter {

    private String filter;
    private Criteria criteria;

    public Filter() {
    }

    public Filter(String filter) throws InvalidODataFormatException {
        this.filter = filter;
        this.criteria = $filterToCriteria(this.filter);
    }

    public static Criteria $filterToCriteria(String $filter) throws InvalidODataFormatException {
        if (hasText($filter)) {
            ImmutableList<String> $filterList = ImmutableList.copyOf(StringUtils.split($filter));
            if (bridgeIsExclusive(AND.toString(), $filterList)) {
                return create$andCriteria($filterListToCriteria(filterExpressionBridgeSet($filterList)));
            } else if (bridgeIsExclusive(OR.toString(), $filterList)) {
                return create$orCriteria($filterListToCriteria(filterExpressionBridgeSet($filterList)));
            } else if ($filterList.size() == 3) {
                return expressionToCriteria(generateExpression($filterList));
            }
        }
        throw new InvalidODataFormatException();
    }

    protected static boolean bridgeIsExclusive(String bridge, List<String> $filterList) {
        HashSet<String> rest = Sets.newHashSet(EXPRESSION_BRIDGE_SET);
        rest.remove(bridge);
        return $filterList.contains(bridge) && $filterList.stream().filter(p -> rest.contains(p)).collect(toList()).isEmpty();
    }

    protected static List<String> filterExpressionBridgeSet(List<String> $filterList) {
        return $filterList.stream().filter(p -> !EXPRESSION_BRIDGE_SET.contains(p)).collect(toList());
    }

    protected static List<Criteria> $filterListToCriteria(List<String> $filterList) {
        return generateCriteriaList(generateExpessionList($filterList));
    }

    protected static List<Criteria> generateCriteriaList(List<Expression> expressionList) {
        List<Criteria> criteriaList = new ArrayList<>();
        for (Expression expression : expressionList) {
            criteriaList.add(expressionToCriteria(expression));
        }
        return criteriaList.stream().filter(criteria -> nonNull(criteria)).collect(toList());
    }

    protected static Criteria expressionToCriteria(Expression expression) {
        Criteria criteria;
        switch (expression.getLogicalOperator()) {
            case EQ:
                criteria = Criteria.where(expression.getKey()).is(expression.getValue());
                break;
            case NE:
                criteria = Criteria.where(expression.getKey()).ne(expression.getValue());
                break;
            case GT:
                criteria = Criteria.where(expression.getKey()).gt(expression.getValue());
                break;
            case GE:
                criteria = Criteria.where(expression.getKey()).gte(expression.getValue());
                break;
            case LT:
                criteria = Criteria.where(expression.getKey()).lt(expression.getValue());
                break;
            case LE:
                criteria = Criteria.where(expression.getKey()).lte(expression.getValue());
                break;
            default:
                criteria = null;
                break;
        }
        return criteria;
    }

    protected static List<Expression> generateExpessionList(List<String> $filterList) {
        List<Expression> expressionList = new ArrayList<>();
        int start = 0, end = 3;
        do {
            expressionList.add(generateExpression($filterList.subList(start, end)));
            start += EXPRESSION_SIZE;
            end += EXPRESSION_SIZE;
        } while ($filterList.size() != start);

        return expressionList;
    }

    protected static Expression generateExpression(List<String> stringList) {
        String value = stringList.get(2);
        Object storedValue;
        if (value.startsWith("datetime")) {
            storedValue = ISO8601DateTimeParser(extractDatetime(value));
        } else if (isSingleQuoted(value)) {
            storedValue = extractString(value);
        } else {
            storedValue = isBoolean(value) ? BooleanUtils.toBoolean(value) : createNumber(value);
        }
        return new Expression(stringList.get(0), BinaryOperatorKind.get(stringList.get(1)), storedValue);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

}
