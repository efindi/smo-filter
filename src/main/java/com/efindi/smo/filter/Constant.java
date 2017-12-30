package com.efindi.smo.filter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import static com.efindi.smo.filter.BinaryOperatorKind.AND;
import static com.efindi.smo.filter.BinaryOperatorKind.OR;

public class Constant {
    public static final int EXPRESSION_SIZE = 3;
    public static final ImmutableSet<String> EXPRESSION_BRIDGE_SET = ImmutableSet.of(AND.toString(), OR.toString());

    public static final String BEGIN = "begin", END = "end";
    public static final ImmutableMap<String, String> PARENTHESES_MAP = ImmutableMap.of(BEGIN, "(", END, ")");
}
