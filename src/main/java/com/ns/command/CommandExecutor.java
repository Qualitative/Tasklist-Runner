package com.ns.command;

import java.io.IOException;
import java.util.List;

import com.ns.exception.ExecutionException;

public interface CommandExecutor {

    List<String> execute(List<String> command) throws IOException, InterruptedException, ExecutionException ;

}
