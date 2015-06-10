package com.ns.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ns.exception.ParseException;
import com.ns.model.Task;

public class VerboseTaskParser implements Parser<List<Task>> {

    private static final Logger LOG = LoggerFactory.getLogger(VerboseTaskParser.class);

    private Parser<Task> parser;

    public VerboseTaskParser(Parser<Task> parser) {
        this.parser = checkNotNull(parser);
    }

    @Override
    public List<Task> parse(String string) throws ParseException {

        if (StringUtils.isEmpty(string)) {
            LOG.warn("String is null or empty");
            throw new ParseException("String is null or empty");
        }

        List<Task> tasks = Lists.newArrayList();

        String[] lines = string.split("\\r?\\n");
        for (String line : lines) {
            Task task;
            try {
                task = parser.parse(line);
            } catch (ParseException e) {
                LOG.warn("Line is skipped due to ParseException");
                continue;
            }
            tasks.add(task);
        }

        return tasks;
    }

}
