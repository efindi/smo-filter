package com.efindi.smo.filter;

import com.efindi.smo.validator.ODataQueryOptionValidator;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.time.DateTimeException;
import java.util.List;

import static com.efindi.smo.filter.Constant.EXPRESSION_BRIDGE_SET;
import static com.efindi.smo.filter.Constant.EXPRESSION_SIZE;
import static com.efindi.smo.util.DateUtils.ISO8601DateTimeParser;
import static com.efindi.smo.util.ODataStringUtils.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.containsAny;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

public class ODataFilterValidatorImpl implements ODataQueryOptionValidator {

    @Override
    public boolean isValid(String $filter) {
        if (isNull($filter)) return true;

        List<String> $filterList = ImmutableList.copyOf(filterPatternSplitToList($filter));
        if (containsAny($filterList, EXPRESSION_BRIDGE_SET))
            return isValidCompoundExpression($filterList);

        if ($filterList.size() == EXPRESSION_SIZE) {
            return isValidExpression($filterList);
        }

        return false;
    }

    private boolean isValidCompoundExpression(List<String> $filterList) {
        try {
            if (!isBridgeIndexValid($filterList))
                return false;

            List<String> newExpression = $filterList.stream().filter(p -> !EXPRESSION_BRIDGE_SET.contains(p)).collect(toList());
            if (newExpression.size() % 3 != 0)
                return false;

            int start = 0, end = 3;
            do {
                if (!isValidExpression(newExpression.subList(start, end)))
                    return false;
                start += EXPRESSION_SIZE;
                end += EXPRESSION_SIZE;
            } while (newExpression.size() != start);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    private boolean isBridgeIndexValid(List<String> $filterList) {
        if (isNotEmpty($filterList) && containsAny($filterList, EXPRESSION_BRIDGE_SET)) {
            String bridge = null;
            for (String s : EXPRESSION_BRIDGE_SET.asList()) {
                if ($filterList.contains(s) && $filterList.indexOf(s) == 3)
                    bridge = s;
            }
            return nonNull(bridge) && ($filterList.indexOf(bridge) + 1) % 4 == 0 ? isBridgeIndexValid($filterList.subList($filterList.indexOf(bridge) + 1, $filterList.size())) : false;
        }

        return true;
    }

    private boolean isValidExpression(List<String> expression) {
        if (expression.size() != EXPRESSION_SIZE)
            return false;

        String logicalOperator = expression.get(1);
        if (isNull(BinaryOperatorKind.get(logicalOperator)))
            return false;

        String value = expression.get(2);
        if (value.startsWith("datetime")) {
            try {
                ISO8601DateTimeParser(extractDatetime(value));
            } catch (DateTimeException | IllegalArgumentException e) {
                return false;
            }
        } else if (!isSingleQuoted(value)) {
            return isBoolean(value) || isCreatable(value);
        }

        return true;
    }

}
