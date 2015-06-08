package com.ns.command;

import java.time.Duration;

import com.ns.exception.ParseException;
import com.ns.model.Status;
import com.ns.model.Task;

public class DetailedTaskParser implements Parser<Task> {

    @Override
    public Task parse(String string) throws ParseException {
        String stringWithoutFirstAndLastChars = string.trim().substring(1, string.length() - 1);
        String[] values = stringWithoutFirstAndLastChars.split("\",\"");

        int numberOfValues = values.length;
        if (numberOfValues != 9) {
            throw new ParseException("Can't parse detailed task string - wrong number of values: " + numberOfValues + ". Expected 9.");
        }

        try {
            Task task = new Task();
            task.setName(values[0]);
            task.setPid(Integer.valueOf(values[1]));
            task.setSessionName(values[2]);
            task.setSessionNumber(Integer.valueOf(values[3]));
            task.setMemoryUsage(getMemoryUsage(values[4]));
            task.setStatus(Status.valueOfIgnoreCase(values[5]));
            task.setUserName(values[6]);
            task.setCpuTime(getCpuTime(values[7]));
            task.setWindowTitle(values[8]);
            return task;
        } catch (Exception e) {
            throw new ParseException("Can't parse detailed task string - malformad value", e);
        }
    }

    private long getMemoryUsage(String string) {
        return Long.valueOf(string.replaceAll("\\D+", ""));
    }

    private Duration getCpuTime(String string) {
        String[] values = string.split(":");
        int hours = Integer.valueOf(values[0]);
        int minutes = Integer.valueOf(values[1]);
        int seconds = Integer.valueOf(values[2]);
        return Duration.ZERO.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

}
