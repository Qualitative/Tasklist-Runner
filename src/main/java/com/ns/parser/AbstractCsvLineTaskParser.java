package com.ns.parser;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.exception.ParseException;
import com.ns.model.Task;

abstract public class AbstractCsvLineTaskParser implements Parser<Task> {

    private final int columnsNumber;

    public AbstractCsvLineTaskParser(int columnsNumber) {
        this.columnsNumber = columnsNumber;
    }

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCsvLineTaskParser.class);

    @Override
    public Task parse(String string) throws ParseException {
        LOG.debug("Parsing [{}] task string", string);

        String[] values = validateStringAndGetValues(string);

        try {
            return createTask(values);
        } catch (Exception e) {
            LOG.warn("Exception occured while parsing task string", e);
            throw new ParseException("Can't parse task string", e);
        }
    }

    protected abstract Task createTask(String[] values);

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
            // TODO: handle case with a string before single quote - to prevent data lose
            LOG.warn("Task string doesn't have mandatory quotes at the line's beginning and ending");
            throw new ParseException("Task string doesn't have mandatory quotes at the line's beginning and ending");
        }

        String stringWithoutFirstAndLastChars = trimmedString.substring(1, trimmedString.length() - 1);
        String[] values = stringWithoutFirstAndLastChars.split("\",\"");

        int numberOfValues = values.length;
        if (numberOfValues != columnsNumber) {
            LOG.warn("String has wrong number of values: {}. Expected {}.", numberOfValues, columnsNumber);
            throw new ParseException("Can't parse task string - wrong number of values: " + numberOfValues
                    + ". Expected " + columnsNumber);
        }
        return values;
    }

}
