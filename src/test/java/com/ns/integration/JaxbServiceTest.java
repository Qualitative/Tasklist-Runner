package com.ns.integration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.google.common.collect.Lists;
import com.ns.model.Task;
import com.ns.oxm.XmlConverter;

@ContextConfiguration(locations = { "/spring/test-jaxb-context.xml", "/spring/jaxb-context.xml" })
public class JaxbServiceTest extends AbstractJUnit4SpringContextTests {

    private File file;

    @Resource
    private XmlConverter<Task> xmlConverter;

    @Before
    public void init() throws IOException {
        file = File.createTempFile("xml-output", null);
        file.deleteOnExit();
    }

    @After
    public void cleanUp() {
        file.delete();
    }

    @Test
    public void shouldConvertTasksToXml() throws Exception {
        List<Task> tasks = Lists.newArrayList(new Task());
        xmlConverter.convertFromListToXml(tasks, file, "tasks");
    }
}
