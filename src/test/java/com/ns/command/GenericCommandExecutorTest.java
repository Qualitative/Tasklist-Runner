package com.ns.command;

import static org.mockito.Mockito.mock;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GenericCommandExecutor.class, File.class })
public class GenericCommandExecutorTest {

    private GenericCommandExecutor executor = new GenericCommandExecutor();

    @Before
    public void init() throws Exception {
        FileInputStream fis = new FileInputStream(getFile("test-command-output.tmp"));
        File mockFile = mock(File.class);
        PowerMockito.mockStatic(File.class);
        when(File.createTempFile("command-output", null)).thenReturn(mockFile);

        Process mockProcess = mock(Process.class);
        ProcessBuilder mockProcessBuilder = PowerMockito.mock(ProcessBuilder.class);
        when(mockProcessBuilder.redirectErrorStream(true)).thenReturn(mockProcessBuilder);
        when(mockProcessBuilder.redirectOutput(mockFile)).thenReturn(mockProcessBuilder);
        when(mockProcessBuilder.start()).thenReturn(mockProcess);

        PowerMockito.whenNew(ProcessBuilder.class).withAnyArguments().thenReturn(mockProcessBuilder);
        PowerMockito.whenNew(FileInputStream.class).withArguments(mockFile).thenReturn(fis);
    }

    @Test
    public void doNOthing() throws Exception {
        List<String> output = executor.execute(Lists.newArrayList("tasklist"));
        // Just for checking, REMOVE it
        System.out.println(output);
    }

    private File getFile(String relativePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(relativePath).getFile());
        return file;
    }

}
