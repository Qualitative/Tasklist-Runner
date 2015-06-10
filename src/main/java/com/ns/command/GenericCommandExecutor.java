package com.ns.command;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.exception.ExecutionException;

public class GenericCommandExecutor implements Executor {

    private static final Logger LOG = LoggerFactory.getLogger(GenericCommandExecutor.class);

    @Override
    public String execute(List<String> command) throws IOException, InterruptedException, ExecutionException {
        LOG.debug("Executing command {}", command);
        LOG.debug("Creating temporary file for command output");
        File tempFile = File.createTempFile("command-output", null);
        try {
            tempFile.deleteOnExit();
            ProcessBuilder processBuilder = new ProcessBuilder(command)
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

    // TODO: handle character encoding issue - OS console is not UTF-8
    private String getCommandOutput(File file) throws IOException {
        LOG.debug("Collecting command's output");
        StringBuilder builder = new StringBuilder();

        try (FileReader fr = new FileReader(file)) {
            int c;
            while ((c = fr.read()) != -1) {
                builder.append((char) c);
            }
        }

        return builder.toString();
    }
}
