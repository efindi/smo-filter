package com.efindi.smo.filter;

import static com.efindi.smo.filter.BinaryOperatorKind.AND;
import static com.efindi.smo.filter.BinaryOperatorKind.OR;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constant {

  public static final int EXPRESSION_SIZE = 3;
  public static final Set<String> EXPRESSION_BRIDGE_SET = Collections
      .unmodifiableSet(Stream.of(AND.toString(), OR.toString()).collect(Collectors.toSet()));

}
