package com.ns.parser;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.ns.exception.ParseException;
import com.ns.model.Task;

abstract public class AbstractCsvLineTaskParserTest {

    private Parser<Task> parser;

    @Before
    public void init() {
        parser = getParser();
    }

    abstract protected Parser<Task> getParser();

    abstract protected int getColumnsNumber();

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenTaskStringHasWrongNumberOfValues() throws Exception {
        // Given
        StringBuilder stringWithWrongNumberOfValues = new StringBuilder();
        for (int i = 0; i < getColumnsNumber() + 1; i++) {
            stringWithWrongNumberOfValues.append("\"");
            stringWithWrongNumberOfValues.append("value" + i);
            stringWithWrongNumberOfValues.append("\"");
            stringWithWrongNumberOfValues.append(",");
        }
        stringWithWrongNumberOfValues.deleteCharAt(stringWithWrongNumberOfValues.length() - 1);

        // When
        parser.parse(stringWithWrongNumberOfValues.toString());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenStringIsNull() throws Exception {
        // When
        parser.parse(null);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenStringIsEmpty() throws Exception {
        // When
        parser.parse(StringUtils.EMPTY);
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenStringContainsSingleQuote() throws Exception {
        // When
        parser.parse("\"");
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenStringDoesNotStartWithQuote() throws Exception {
        // When
        parser.parse("\"some string");
    }

    @Test(expected = ParseException.class)
    public void shouldThrowExceptionWhenStringDoesNotEndWithQuote() throws Exception {
        // When
        parser.parse("some string\"");
    }

}
