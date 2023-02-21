package com.efindi.smo.filter;

public class Expression {

    private String key;
    private BinaryOperatorKind logicalOperator;
    private Object value;

    public Expression() {
    }

    public Expression(String key, BinaryOperatorKind logicalOperator, Object value) {
        this.key = key;
        this.logicalOperator = logicalOperator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BinaryOperatorKind getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(BinaryOperatorKind logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
