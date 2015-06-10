package com.ns.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.ns.exception.ParseException;
import com.ns.model.Task;

@RunWith(MockitoJUnitRunner.class)
public class VerboseTaskParserTest {

    private static final Task TASK_A = new Task();
    private static final Task TASK_B = new Task();
    private static final String TASK_A_STRING = "a";
    private static final String TASK_B_STRING = "b";
    private static final String OUTPUT = TASK_A_STRING + "\n" + TASK_B_STRING;

    @Mock
    private VerboseLineTaskParser lineParser;

    private VerboseTaskParser parser;

    @Before
    public void init() throws Exception {
        TASK_A.setName(TASK_A_STRING);
        TASK_B.setName(TASK_B_STRING);
        when(lineParser.parse(TASK_A_STRING)).thenReturn(TASK_A);
        when(lineParser.parse(TASK_B_STRING)).thenReturn(TASK_B);

        parser = new VerboseTaskParser(lineParser);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenLineParserIsNull() {
        new VerboseTaskParser(null);
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

    @Test
    public void shouldReturnTasksListWhenAllLinesSuccessfullyParsed() throws Exception {
        // Given
        List<Task> expectedTasksList = Lists.newArrayList(TASK_A, TASK_B);
        // When
        List<Task> actualTasksList = parser.parse(OUTPUT);
        // Then
        assertEquals(expectedTasksList, actualTasksList);
        verifyMocks();
    }

    @Test
    public void shouldReturnEmptyTasksListWhenAllLinesAreSkipped() throws Exception {
        // Given
        List<Task> expectedTasksList = Lists.newArrayList();
        when(lineParser.parse(TASK_A_STRING)).thenThrow(new ParseException(TASK_A_STRING));
        when(lineParser.parse(TASK_B_STRING)).thenThrow(new ParseException(TASK_B_STRING));
        // When
        List<Task> actualTasksList = parser.parse(OUTPUT);
        // Then
        assertEquals(expectedTasksList, actualTasksList);
        verifyMocks();
    }

    @Test
    public void shouldReturnTasksListWhenSomeLinesAreSkipped() throws Exception {
        // Given
        List<Task> expectedTasksList = Lists.newArrayList(TASK_B);
        when(lineParser.parse(TASK_A_STRING)).thenThrow(new ParseException(TASK_A_STRING));
        // When
        List<Task> actualTasksList = parser.parse(OUTPUT);
        // Then
        assertEquals(expectedTasksList, actualTasksList);
        verifyMocks();
    }

    private void verifyMocks() throws ParseException {
        verify(lineParser, times(2)).parse(Mockito.anyString());
        verifyNoMoreInteractions(lineParser);
    }
}
