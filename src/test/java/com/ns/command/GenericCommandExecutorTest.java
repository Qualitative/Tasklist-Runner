package com.ns.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Lists;
import com.ns.exception.ExecutionException;
import com.ns.util.InputOutputUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GenericCommandExecutor.class, File.class, InputOutputUtils.class })
public class GenericCommandExecutorTest {

    private static final String FILE_NAME = "test-command-output.tmp";

    // check line separator value - it should be the same in file and in this string
    private static final String EXPECTED_OUTPUT = "\"razertra.exe\",\"1412\",\"Console\",\"1\",\"6 724 КБ\",\"Running\",\"aGressOr-PC\\aGressOr\",\"0:00:00\",\"RazerAbyssusTrayIcon\"\n"
            + "second string\n" + "another not important string";

    private static final List<String> COMMAND = Lists.newArrayList("any", "command");
    private static final List<String> WRAPPED_COMMAND = Lists.newArrayList("cmd", "/c", "chcp", "65001", "&", "any",
            "command");

    private File file = mock(File.class);
    private Process process = mock(Process.class);
    private ProcessBuilder processBuilder = PowerMockito.mock(ProcessBuilder.class);

    private GenericCommandExecutor executor = new GenericCommandExecutor();

    @Before
    public void init() throws Exception {
        PowerMockito.mockStatic(File.class);
        when(File.createTempFile("command-output", null)).thenReturn(file);

        when(processBuilder.redirectErrorStream(true)).thenReturn(processBuilder);
        when(processBuilder.redirectOutput(file)).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);
        when(process.waitFor()).thenReturn(0);

        FileInputStream fis = new FileInputStream(getFile(FILE_NAME));

        PowerMockito.whenNew(ProcessBuilder.class).withArguments(WRAPPED_COMMAND).thenReturn(processBuilder);
        PowerMockito.whenNew(FileInputStream.class).withArguments(file).thenReturn(fis);
    }

    @Test
    public void shouldRedirectCommandOutputToFileAndThenReturnItsLines() throws Exception {
        // When
        String output = executor.execute(COMMAND);
        // Then
        assertEquals(EXPECTED_OUTPUT, output);
        verifyMocks();
    }

    @Test
    public void shouldThrowExceptionWhenCommandReturnsNonZeroExitCode() throws Exception {
        // Given
        when(process.waitFor()).thenReturn(1);
        // When
        try {
            executor.execute(COMMAND);
            throw new IllegalStateException("Should not reach this line");
        } catch (ExecutionException e) {
            verifyMocks();
        }
    }

    private void verifyMocks() throws InterruptedException {
        verify(file).deleteOnExit();
        verify(file).delete();
        verify(process).waitFor();
        verifyNoMoreInteractions(file, process);
    }

    private File getFile(String relativePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(relativePath).getFile());
        return file;
    }

}
