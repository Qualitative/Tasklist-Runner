package com.ns;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ns.gui.MainWindow;
import com.ns.gui.controller.GuiController;

@SuppressWarnings({ "resource" })
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.debug("Start application");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "spring/command-execution-service-context.xml");
        GuiController controller = (GuiController) applicationContext.getBean("guiController");
        SwingUtilities.invokeLater(() -> createAndShowGui(controller));
    }

    private static void createAndShowGui(GuiController controller) {
        MainWindow window = new MainWindow(controller);
        window.init();
        controller.runCommand();
        window.setVisible(true);
    }
}
