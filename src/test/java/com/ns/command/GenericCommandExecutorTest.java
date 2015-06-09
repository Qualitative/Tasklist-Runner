package com.ns.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.gradle.jarjar.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ns.exception.ExecutionException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GenericCommandExecutor.class, File.class })
public class GenericCommandExecutorTest {

    private static final String FILE_NAME = "test-command-output.tmp";

    private static final String FIRST_LINE = "\"razertra.exe\",\"1412\",\"Console\",\"1\",\"6 724 КБ\",\"Running\",\"aGressOr-PC\\aGressOr\",\"0:00:00\",\"RazerAbyssusTrayIcon\"";
    private static final String SECOND_LINE = "second string";
    private static final String THIRD_LINE = "another not important string";

    private static final List<String> EXPECTED_OUTPUT = Lists.newArrayList(FIRST_LINE, SECOND_LINE, THIRD_LINE);

    private static final List<String> COMMAND = Lists.newArrayList("any", "command");

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

        PowerMockito.whenNew(ProcessBuilder.class).withArguments(COMMAND).thenReturn(processBuilder);
        PowerMockito.whenNew(FileInputStream.class).withArguments(file).thenReturn(fis);
    }

    @Test
    public void shouldRedirectCommandOutputToFileAndThenReturnItsLines() throws Exception {
        // When
        List<String> output = executor.execute(COMMAND);
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
