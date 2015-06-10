package com.ns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
import com.ns.configuration.CommandConfiguration;
import com.ns.model.Task;

@SuppressWarnings({ "resource", "unchecked" })
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.debug("Start application");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/application-context.xml");
        CommandConfiguration<List<Task>> tasklist = (CommandConfiguration<List<Task>>) applicationContext
                .getBean("tasklistConfiguration");
        tasklist.run(Lists.newArrayList("tasklist", "/v", "/fo", "csv", "/nh"));

    }
}
