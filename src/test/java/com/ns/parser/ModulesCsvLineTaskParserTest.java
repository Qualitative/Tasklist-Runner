package com.ns.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.ns.exception.ParseException;
import com.ns.model.Task;

public class ModulesCsvLineTaskParserTest extends AbstractCsvLineTaskParserTest {

    private static final int COLUMNS_NUMBER = 3;
    private static final String POINT = ".";
    private static final String COMMA = ",";
    private static final String QUOTE = "\"";
    private static final String QUOTE_COMMA_QUOTE = "\",\"";
    private static final String EXPECTED_NAME = "System Idle Process";
    private static final int EXPECTED_PID = 123;
    private static final String MODULE1 = "Module1";
    private static final String MODULE2 = "Module2";
    private static final String MODULE3 = "Module3";

    private static final List<String> EXPECTED_MODULES = Lists.newArrayList(MODULE1, MODULE2, MODULE3);
    private static final List<String> EXPECTED_SERVICES = Lists.newArrayList();

    private static final StringBuilder STRING_BUILDER = new StringBuilder().append(QUOTE).append(EXPECTED_NAME)
            .append(QUOTE_COMMA_QUOTE).append(EXPECTED_PID).append(QUOTE_COMMA_QUOTE).append(MODULE1).append(COMMA)
            .append(MODULE2).append(COMMA).append(MODULE3).append(QUOTE);

    private static final String TASK_STRING = STRING_BUILDER.toString();

    private ModulesCsvLineTaskParser parser;

    @Override
    protected Parser<Task> getParser() {
        parser = new ModulesCsvLineTaskParser(COLUMNS_NUMBER);
        return parser;
    }

    @Override
    protected int getColumnsNumber() {
        return COLUMNS_NUMBER;
    }

    @Test
    public void shouldReturnTaskWithCorrectValuesWhenParseDetailedString() throws Exception {
        // When
        Task actualTask = parser.parse(TASK_STRING);
        // Then
        assertEquals(EXPECTED_NAME, actualTask.getName());
        assertEquals(EXPECTED_PID, actualTask.getPid());
        assertNull(actualTask.getSessionName());
        assertEquals(Task.DEFAULT, actualTask.getSessionNumber());
        assertEquals(Task.DEFAULT, actualTask.getMemoryUsage());
        assertNull(actualTask.getStatus());
        assertNull(actualTask.getUserName());
        assertNull(actualTask.getCpuTime());
        assertNull(actualTask.getWindowTitle());
        assertEquals(EXPECTED_MODULES, actualTask.getModules());
        assertEquals(EXPECTED_SERVICES, actualTask.getServices());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenSomeValueIsMalformed() throws Exception {
        // Given
        String malformedString = TASK_STRING.replaceAll(COMMA, POINT);
        // When
        parser.parse(malformedString);
    }

}
