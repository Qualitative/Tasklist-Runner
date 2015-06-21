package com.ns.filter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ns.model.Status;

// TODO: cover other filters
public class TasklistFilterBuilderTest {

    private TasklistFilterBuilder builder;

    @Before
    public void init() {
        builder = new TasklistFilterBuilder();
    }

    @Test
    public void shouldBuildCorrectCommandListForStatusFilter() {
        // Given
        Map<LogicOperator, List<Status>> criteria = Maps.newLinkedHashMap();
        criteria.put(LogicOperator.EQUALS, Lists.newArrayList(Status.RUNNING, Status.UNKNOWN));
        criteria.put(LogicOperator.NOT_EQUALS, Lists.newArrayList(Status.NOT_RESPONDING));
        // When
        List<String> filter = builder.addStatusFilter(criteria, false).build();
        // Then
        assertEquals(6, filter.size());
        assertEquals(TasklistFilterBuilder.FI, filter.get(0));
        assertEquals("\"STATUS eq Running\"", filter.get(1));
        assertEquals(TasklistFilterBuilder.FI, filter.get(2));
        assertEquals("\"STATUS eq Unknown\"", filter.get(3));
        assertEquals(TasklistFilterBuilder.FI, filter.get(4));
        assertEquals("\"STATUS ne Not Responding\"", filter.get(5));
    }

}
