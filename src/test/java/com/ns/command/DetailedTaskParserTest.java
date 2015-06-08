package com.ns.command;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.ns.exception.ParseException;
import com.ns.model.Status;
import com.ns.model.Task;


public class DetailedTaskParserTest {

    private static final String COLON = ":";
    private static final String SEMICOLON = ";";
    private static final String QUOTE = "\"";
    private static final String QUOTE_COMMA_QUOTE = "\",\"";
    private static final String MEMORY_SUFFIX = " ANY String";
    private static final int HOURS = 7;
    private static final int MINUTES = 41;
    private static final int SECONDS = 22;
    private static final String EXPECTED_NAME = "System Idle Process";
    private static final int EXPECTED_PID = 123;
    private static final String EXPECTED_SESSION_NAME = "Services";
    private static final int EXPECTED_SESSION_NUMBER = 12345;
    private static final long EXPECTED_MEMORY_USAGE = 1234567;
    private static final Status EXPECTED_STATUS = Status.UNKNOWN;
    private static final String EXPECTED_USER_NAME = "NT AUTHORITY\\СИСТЕМА";
    private static final Duration EXPECTED_CPU_TIME = Duration.ZERO.plusHours(HOURS).plusMinutes(MINUTES).plusSeconds(SECONDS);
    private static final String EXPECTED_WINDOW_TITLE = "Н/Д";
    private static final List<String> EXPECTED_MODULES = Lists.newArrayList();
    private static final List<String> EXPECTED_SERVICES = Lists.newArrayList();

    private static final StringBuilder STRING_BUILDER = new StringBuilder()
                        .append(QUOTE).append(EXPECTED_NAME)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_PID)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_SESSION_NAME)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_SESSION_NUMBER)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_MEMORY_USAGE).append(MEMORY_SUFFIX)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_STATUS.name())
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_USER_NAME)
                        .append(QUOTE_COMMA_QUOTE).append(HOURS).append(COLON).append(MINUTES).append(COLON).append(SECONDS)
                        .append(QUOTE_COMMA_QUOTE).append(EXPECTED_WINDOW_TITLE)
                        .append(QUOTE);

    private static final String DETAILED_TASK_STRING = STRING_BUILDER.toString();

    private static final String TASK_STRING_WITH_WRONG_NUMBER_OF_VALUES = "\"Some task name\",\"and service only\"";

    private DetailedTaskParser parser;

    @Before
    public void init() {
        parser = new DetailedTaskParser();
    }

    @Test
    public void shouldReturnTaskWithCorrectValuesWhenParseDetailedString() throws Exception {
        // When
        Task actualTask = parser.parse(DETAILED_TASK_STRING);
        // Then
        assertEquals(EXPECTED_NAME, actualTask.getName());
        assertEquals(EXPECTED_PID, actualTask.getPid());
        assertEquals(EXPECTED_SESSION_NAME, actualTask.getSessionName());
        assertEquals(EXPECTED_SESSION_NUMBER, actualTask.getSessionNumber());
        assertEquals(EXPECTED_MEMORY_USAGE, actualTask.getMemoryUsage());
        assertEquals(EXPECTED_STATUS, actualTask.getStatus());
        assertEquals(EXPECTED_USER_NAME, actualTask.getUserName());
        assertEquals(EXPECTED_CPU_TIME, actualTask.getCpuTime());
        assertEquals(EXPECTED_WINDOW_TITLE, actualTask.getWindowTitle());
        assertEquals(EXPECTED_MODULES, actualTask.getModules());
        assertEquals(EXPECTED_SERVICES, actualTask.getServices());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenTaskStringHasWrongNumberOfValues() throws Exception {
        // When
        parser.parse(TASK_STRING_WITH_WRONG_NUMBER_OF_VALUES);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenSomeValueIsMalformed() throws Exception {
        // Given
        String malformedString = DETAILED_TASK_STRING.replaceAll(COLON, SEMICOLON);
        // When
        parser.parse(malformedString);
    }

}
