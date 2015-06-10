package com.ns.configuration;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.ns.command.Executor;
import com.ns.parser.Parser;
import com.ns.processor.Processor;

public class CommandConfiguration<T> {

    private Executor executor;
    private Parser<T> parser;
    private Processor<T> processor;

    public CommandConfiguration(Executor executor, Parser<T> parser, Processor<T> processor) {
        this.executor = checkNotNull(executor);
        this.parser = checkNotNull(parser);
        this.processor = checkNotNull(processor);
    }

    public void run(List<String> command) {
        // TODO: provide correct exception handling
        try {
            String commandOutput = executor.execute(command);
            T item = parser.parse(commandOutput);
            processor.process(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
