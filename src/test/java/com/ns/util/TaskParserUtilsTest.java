package com.ns.util;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.Test;


public class TaskParserUtilsTest {

    private static final int MEMORY_USAGE = 1234;
    private static final String MEMORY_USAGE_STRING = "" + MEMORY_USAGE + " Kb";

    private static final String CPU_TIME_STRING = "23:01:12";
    private static final Duration CPU_TIME = Duration.ZERO.plusHours(23).plusMinutes(1).plusSeconds(12);

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenMemoryUsageStringIsNull() {
        // When
        TaskParserUtils.getMemoryUsage(null);
    }

    @Test
    public void shouldReturnOnlyDigitsWhenGettingMemoryValue() {
        // When
        long memoryUsage = TaskParserUtils.getMemoryUsage(MEMORY_USAGE_STRING);
        // Then
        assertEquals(MEMORY_USAGE, memoryUsage);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenCpuTimeStringIsNull() {
        // When
        TaskParserUtils.getCpuTime(null);
    }

    @Test
    public void shouldReturnDurationWhenGettingCpuTime() {
        // When
        Duration cpuTime = TaskParserUtils.getCpuTime(CPU_TIME_STRING);
        // Then
        assertEquals(CPU_TIME, cpuTime);
    }

}
