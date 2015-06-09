package com.ns.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ns.exception.ExecutionException;

public class GenericCommandExecutor implements CommandExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(GenericCommandExecutor.class);

    @Override
    public List<String> execute(List<String> command) throws IOException, InterruptedException, ExecutionException {
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
                LOG.debug("CommandExecutor is finished successfully");
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

    private List<String> getCommandOutput(File file) throws IOException {
        LOG.debug("Collecting command's output");
        List<String> result = Lists.newArrayList();

        try (InputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        }

        return result;
    }
}
