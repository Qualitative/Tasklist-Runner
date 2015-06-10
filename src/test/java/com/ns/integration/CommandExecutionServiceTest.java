package com.ns.integration;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.google.common.collect.Lists;
import com.ns.configuration.CommandConfiguration;
import com.ns.model.Task;

@ContextConfiguration(locations = {
        "/spring/test-command-execution-service-context.xml",
        "/spring/command-execution-service-context.xml"
})
public class CommandExecutionServiceTest extends AbstractJUnit4SpringContextTests {

    @Resource(name = "tasklistConfiguration")
    private CommandConfiguration<List<Task>> configuration;

    @Test
    public void testContext() {
        configuration.run(Lists.newArrayList("tasklist", "/v", "/fo", "csv", "/nh"));
    }
}
