package com.efindi.smo.filter;

import static com.efindi.smo.filter.BinaryOperatorKind.AND;
import static com.efindi.smo.filter.BinaryOperatorKind.OR;

import com.google.common.collect.ImmutableSet;

public class Constant {

  public static final int EXPRESSION_SIZE = 3;
  public static final ImmutableSet<String> EXPRESSION_BRIDGE_SET = ImmutableSet
      .of(AND.toString(), OR.toString());
}
