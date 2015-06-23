package com.ns.filter;

public class Filter {

    private FilterName name;
    private LogicOperator operator;
    private String value;

    public Filter(FilterName name, LogicOperator operator, String value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }

    public FilterName getName() {
        return name;
    }

    public LogicOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public void setName(FilterName name) {
        this.name = name;
    }

    public void setOperator(LogicOperator operator) {
        this.operator = operator;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
