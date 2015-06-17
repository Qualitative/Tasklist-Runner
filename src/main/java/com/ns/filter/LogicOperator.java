package com.ns.filter;

public enum LogicOperator {
    EQUALS("eq"),
    NOT_EQUALS("ne"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUALS("ge"),
    LESS_THAN_OR_EQUALS("le");

    private LogicOperator(String shortForm) {
        this.shortForm = shortForm;
    }

    public String getShortForm() {
        return shortForm;
    }

    private String shortForm;

}
