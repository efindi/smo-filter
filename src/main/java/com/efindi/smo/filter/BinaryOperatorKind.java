package com.efindi.smo.filter;

/**
 * Enumeration of supported binary operators<br> For the semantic of these operators please see the
 * ODATA specification for URL conventions
 */
public enum BinaryOperatorKind {

  /**
   * Greater than operator ("{@literal >}")
   */
  GT("gt"),

  /**
   * Greater than or equals ("{@literal >=}") operator
   */
  GE("ge"),

  /**
   * Lesser than operator ("{@literal <}")
   */
  LT("lt"),

  /**
   * Lesser operator or equals ("{@literal <=}") operator
   */
  LE("le"),

  /**
   * Equality operator
   */
  EQ("eq"),

  /**
   * Inequality operator
   */
  NE("ne"),

  /**
   * And operator
   */
  AND("and"),

  /**
   * Or operator
   */
  OR("or");

  private String syntax;

  /**
   * Constructor for enumeration value
   *
   * @param syntax used in the URI
   */
  private BinaryOperatorKind(final String syntax) {
    this.syntax = syntax;
  }

  /**
   * URI syntax to enumeration value
   *
   * @param operator Operator in the syntax used in the URI
   * @return Operator kind which represents the given syntax
   */
  public static BinaryOperatorKind get(final String operator) {
    for (BinaryOperatorKind op : BinaryOperatorKind.values()) {
      if (op.toString().equals(operator)) {
        return op;
      }
    }
    return null;
  }

  /**
   * @return URI syntax for that operator kind
   */
  @Override
  public String toString() {
    return syntax;
  }

}
