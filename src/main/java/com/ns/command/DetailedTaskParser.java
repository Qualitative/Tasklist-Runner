package com.ns.command;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.exception.ParseException;
import com.ns.model.Status;
import com.ns.model.Task;

public class DetailedTaskParser implements Parser<Task> {

    private static final Logger LOG = LoggerFactory.getLogger(DetailedTaskParser.class);

    @Override
    public Task parse(String string) throws ParseException {
        LOG.debug("Parsing [{}] detailed task string", string);
        String stringWithoutFirstAndLastChars = string.trim().substring(1, string.length() - 1);
        String[] values = stringWithoutFirstAndLastChars.split("\",\"");

        int numberOfValues = values.length;
        if (numberOfValues != 9) {
            LOG.error("String has wrong number of values: {}. Expected 9.", numberOfValues);
            throw new ParseException("Can't parse detailed task string - wrong number of values: " + numberOfValues
                    + ". Expected 9.");
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
            LOG.debug("Created {}", task);
            return task;
        } catch (Exception e) {
            LOG.error("Exception occured while parsing task string", e);
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
