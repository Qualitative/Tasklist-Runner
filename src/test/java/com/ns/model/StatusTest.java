package com.ns.model;

import org.junit.Assert;
import org.junit.Test;

public class StatusTest {

    @Test
    public void shouldReturnCorrectStatusWhenValueOfIsInvokedWithCorrectName() {
        // When
        Status actualStatus = Status.valueOfIgnoreCase("noT respoNDInG");
        // Then
        Assert.assertEquals(Status.NOT_RESPONDING, actualStatus);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenValueOfIsInvokedWithWrongName() {
        // When
        Status.valueOfIgnoreCase("some nonexistent status");
    }

}
