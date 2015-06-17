package com.ns.filter;

import static com.ns.filter.LogicOperator.EQUALS;
import static com.ns.filter.LogicOperator.GREATER_THAN;
import static com.ns.filter.LogicOperator.GREATER_THAN_OR_EQUALS;
import static com.ns.filter.LogicOperator.LESS_THAN;
import static com.ns.filter.LogicOperator.LESS_THAN_OR_EQUALS;
import static com.ns.filter.LogicOperator.NOT_EQUALS;

import java.util.Set;

import com.google.common.collect.Sets;

public enum FilterName {
    STATUS(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    IMAGENAME(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    PID(Sets.newHashSet(EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS)),
    SESSION(Sets.newHashSet(EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS)),
    SESSIONNAME(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    CPUTIME(Sets.newHashSet(EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS)),
    MEMUSAGE(Sets.newHashSet(EQUALS, NOT_EQUALS, GREATER_THAN, LESS_THAN, GREATER_THAN_OR_EQUALS, LESS_THAN_OR_EQUALS)),
    USERNAME(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    SERVICES(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    WINDOWTITILE(Sets.newHashSet(EQUALS, NOT_EQUALS)),
    MODULES(Sets.newHashSet(EQUALS, NOT_EQUALS));


    private FilterName(Set<LogicOperator> validOperators) {
        this.validOperators = validOperators;
    }

    public Set<LogicOperator> getValidOperators() {
        return validOperators;
    }

    private Set<LogicOperator> validOperators;
}
