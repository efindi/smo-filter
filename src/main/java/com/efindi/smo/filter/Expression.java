package com.efindi.smo.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expression {

  private String key;
  private BinaryOperatorKind logicalOperator;
  private Object value;

}
