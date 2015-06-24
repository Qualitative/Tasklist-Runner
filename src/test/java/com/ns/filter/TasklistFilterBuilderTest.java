package com.ns.filter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.ns.model.Status;

public class TasklistFilterBuilderTest {

    private TasklistFilterBuilder builder;

    @Before
    public void init() {
        builder = new TasklistFilterBuilder();
    }

    @Test
    public void shouldBuildCorrectCommandList() {
        // Given
        Filter filter1 = new Filter(FilterName.STATUS, LogicOperator.EQUALS, Status.UNKNOWN.toString());
        Filter filter2 = new Filter(FilterName.STATUS, LogicOperator.NOT_EQUALS, Status.NOT_RESPONDING.toString());
        List<Filter> filters = Lists.newArrayList(filter1, filter2);
        // When
        List<String> filter = builder.buildFiltersCommand(filters);
        // Then
        assertEquals(4, filter.size());
        assertEquals(TasklistFilterBuilder.FI, filter.get(0));
        assertEquals("\"STATUS eq Unknown\"", filter.get(1));
        assertEquals(TasklistFilterBuilder.FI, filter.get(2));
        assertEquals("\"STATUS ne Not Responding\"", filter.get(3));
    }

}
