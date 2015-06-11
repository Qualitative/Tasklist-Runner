package com.ns.parser;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.exception.ParseException;
import com.ns.model.Status;
import com.ns.model.Task;

// TODO: extract generic logic to abstract class. Use template pattern
public class VerboseCsvLineTaskParser implements Parser<Task> {

    private static final Logger LOG = LoggerFactory.getLogger(VerboseCsvLineTaskParser.class);

    @Override
    public Task parse(String string) throws ParseException {
        LOG.debug("Parsing [{}] detailed task string", string);

        String[] values = validateStringAndGetValues(string);

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
            LOG.warn("Exception occured while parsing task string", e);
            throw new ParseException("Can't parse detailed task string - malformad value", e);
        }
    }

    private String[] validateStringAndGetValues(String string) throws ParseException {
        if (StringUtils.isEmpty(string)) {
            LOG.warn("Task string is null or empty");
            throw new ParseException("Task string is null or empty");
        }

        String trimmedString = string.trim();

        if (trimmedString.startsWith("\"") && trimmedString.endsWith("\"")) {
            if (trimmedString.length() < 2) {
                // covers case when string contains single quote (faced it on real PC)
                LOG.warn("Task string is a quote. (Very likely command produced output when line's ending quote became on new line)");
                throw new ParseException("Task string is a quote");
            }
        } else {
            //TODO: handle case with a string before single quote - to prevent data lose
            LOG.warn("Task string doesn't have mandatory quotes at the line's beginning and ending");
            throw new ParseException("Task string doesn't have mandatory quotes at the line's beginning and ending");
        }

        String stringWithoutFirstAndLastChars = trimmedString.substring(1, trimmedString.length() - 1);
        String[] values = stringWithoutFirstAndLastChars.split("\",\"");

        int numberOfValues = values.length;
        if (numberOfValues != 9) {
            LOG.warn("String has wrong number of values: {}. Expected 9.", numberOfValues);
            throw new ParseException("Can't parse detailed task string - wrong number of values: " + numberOfValues
                    + ". Expected 9.");
        }
        return values;
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
