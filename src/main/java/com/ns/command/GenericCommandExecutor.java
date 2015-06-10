package com.ns.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ns.exception.ExecutionException;

public class GenericCommandExecutor implements Executor {

    private static final Logger LOG = LoggerFactory.getLogger(GenericCommandExecutor.class);

    @Override
    public String execute(List<String> command) throws IOException, InterruptedException, ExecutionException {
        LOG.debug("Executing command {}", command);

        List<String> wrappedCommand = wrapWithUTF8Console(command);

        LOG.debug("Creating temporary file for command output");
        File tempFile = File.createTempFile("command-output", null);
        try {
            tempFile.deleteOnExit();
            ProcessBuilder processBuilder = new ProcessBuilder(wrappedCommand)
                                                    .redirectErrorStream(true)
                                                    .redirectOutput(tempFile);
            Process process = processBuilder.start();
            LOG.debug("Waiting for command execution finish");
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                LOG.debug("Executor is finished successfully");
                return getCommandOutput(tempFile);
            } else {
                LOG.error("Unexpected exit code - {}", exitCode);
                throw new ExecutionException("", exitCode);
            }
        } finally {
            LOG.debug("Removing temporary file for command output");
            tempFile.delete();
        }
    }

    private List<String> wrapWithUTF8Console(List<String> command) {
        List<String> result = Lists.newArrayList("cmd", "/c", "chcp","65001", "&");
        result.addAll(command);
        return result;
    }

    private String getCommandOutput(File file) throws IOException {
        LOG.debug("Collecting command's output");
        StringBuilder builder = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader in = new BufferedReader(isr);) {
            int c;
            while ((c = in.read()) != -1) {
                builder.append((char) c);
            }
        }

        return builder.toString();
    }

}
