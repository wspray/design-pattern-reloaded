package handWrite.mini_spring.web;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.bridge.SLF4JBridgeHandler;
import handWrite.mini_spring.Autowired;
import handWrite.mini_spring.Component;
import handWrite.mini_spring.PostConstruct;

import java.io.File;
import java.util.logging.LogManager;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
@Component
public class TomcatServer {


    @Autowired
    private DispatcherServlet dispatcherServlet;

    @PostConstruct
    public void start() throws LifecycleException {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        int port = 8080;
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        tomcat.addServlet(contextPath, "dispatcherServlet", dispatcherServlet);
        context.addServletMappingDecoded("/*", "dispatcherServlet");
        tomcat.start();
        System.out.println("tomcat start...  port :" + port);
    }
}
