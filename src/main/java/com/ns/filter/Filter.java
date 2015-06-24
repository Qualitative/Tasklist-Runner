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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Filter other = (Filter) obj;
        if (name != other.name)
            return false;
        if (operator != other.operator)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Filter [name=" + name + ", operator=" + operator.getShortForm() + ", value=" + value + "]";
    }

}
