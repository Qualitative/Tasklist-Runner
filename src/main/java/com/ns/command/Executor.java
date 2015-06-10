package com.ns.command;

import java.io.IOException;
import java.util.List;

import com.ns.exception.ExecutionException;

public interface Executor {

    String execute(List<String> command) throws IOException, InterruptedException, ExecutionException ;

}
