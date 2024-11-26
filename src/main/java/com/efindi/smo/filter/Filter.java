package com.efindi.smo.filter;

import static com.efindi.smo.filter.BinaryOperatorKind.AND;
import static com.efindi.smo.filter.BinaryOperatorKind.OR;
import static com.efindi.smo.filter.Constant.EXPRESSION_BRIDGE_SET;
import static com.efindi.smo.filter.Constant.EXPRESSION_SIZE;
import static com.efindi.smo.util.DateUtils.ISO8601DateTimeParser;
import static com.efindi.smo.util.MongoUtils.create$andCriteria;
import static com.efindi.smo.util.MongoUtils.create$orCriteria;
import static com.efindi.smo.util.ODataStringUtils.extractDatetime;
import static com.efindi.smo.util.ODataStringUtils.extractString;
import static com.efindi.smo.util.ODataStringUtils.filterPatternSplitToList;
import static com.efindi.smo.util.ODataStringUtils.isBoolean;
import static com.efindi.smo.util.ODataStringUtils.isSingleQuoted;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createNumber;

import com.efindi.smo.exception.InvalidODataFormatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * Filter string must be validated by ODataQueryOptionValidator prior to object creation.
 */
@Getter
public class Filter {

  protected String filter;
  protected Criteria criteria;

  public Filter(String filter) throws InvalidODataFormatException {
    this.filter = filter;
    this.criteria = $filterToCriteria(this.filter);
  }

  public static Filter of(String filter) throws InvalidODataFormatException {
    return new Filter(filter);
  }

  protected Criteria $filterToCriteria(String $filter) throws InvalidODataFormatException {
    if (isNotEmpty($filter)) {
      List<String> $filterList = Collections.unmodifiableList(filterPatternSplitToList($filter));
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

  protected boolean bridgeIsExclusive(String bridge, List<String> $filterList) {
    final Set<String> rest = new HashSet<>(EXPRESSION_BRIDGE_SET);
    rest.remove(bridge);
    return $filterList.contains(bridge) && $filterList.stream().noneMatch(rest::contains);
  }

  protected List<String> filterExpressionBridgeSet(List<String> $filterList) {
    return $filterList.stream().filter(p -> !EXPRESSION_BRIDGE_SET.contains(p)).collect(toList());
  }

  protected List<Criteria> $filterListToCriteria(List<String> $filterList) {
    return generateCriteriaList(generateExpessionList($filterList));
  }

  protected List<Criteria> generateCriteriaList(List<Expression> expressionList) {
    final List<Criteria> criteriaList = new ArrayList<>();
    for (Expression expression : expressionList) {
      criteriaList.add(expressionToCriteria(expression));
    }
    return criteriaList.stream().filter(Objects::nonNull).collect(toList());
  }

  protected Criteria expressionToCriteria(Expression expression) {
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

  protected List<Expression> generateExpessionList(List<String> $filterList) {
    final List<Expression> expressionList = new ArrayList<>();
    int start = 0, end = 3;
    do {
      expressionList.add(generateExpression($filterList.subList(start, end)));
      start += EXPRESSION_SIZE;
      end += EXPRESSION_SIZE;
    } while ($filterList.size() != start);

    return expressionList;
  }

  protected Expression generateExpression(List<String> stringList) {
    final String value = stringList.get(2);
    Object storedValue;
    if (value.startsWith("datetime")) {
      storedValue = ISO8601DateTimeParser(extractDatetime(value));
    } else if (isSingleQuoted(value)) {
      storedValue = extractString(value);
    } else {
      storedValue = isBoolean(value) ? BooleanUtils.toBoolean(value) : createNumber(value);
    }
    return new Expression(stringList.get(0), BinaryOperatorKind.get(stringList.get(1)),
        storedValue);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("filter", filter)
        .append("criteria", criteria.getCriteriaObject())
        .toString();
  }
}
