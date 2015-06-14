package com.ns.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.Duration;

public class TaskParserUtils {

    public static long getMemoryUsage(String memoryUsageString) {
        checkNotNull(memoryUsageString);
        return Long.valueOf(memoryUsageString.replaceAll("\\D+", ""));
    }

    public static Duration getCpuTime(String cpuTimeString) {
        checkNotNull(cpuTimeString);
        String[] values = cpuTimeString.split(":");
        int hours = Integer.valueOf(values[0]);
        int minutes = Integer.valueOf(values[1]);
        int seconds = Integer.valueOf(values[2]);
        return Duration.ZERO.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

}
