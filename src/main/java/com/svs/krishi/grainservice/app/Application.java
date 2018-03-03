package com.svs.krishi.grainservice.app;

import com.svs.krishi.grainservice.app.filter.RequestIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * This is the application for the grain service.
 */
@Slf4j
public class Application {

    public static void main(String[] args) throws Exception {
        log.info("Initializing the server to handle requests");
        final Server server = new Server();

        final ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("com.svs.krishi.grainservice.app.resources");
        resourceConfig.register(JacksonFeature.class);
        final ServletHolder servlet = new ServletHolder(new ServletContainer(resourceConfig));

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addFilter(RequestIdFilter.class,
            "/*",
            EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC));
        context.addServlet(servlet, "/*");

        try {
            final ServerConnector connector = new ServerConnector(server);
            connector.setPort(8888);
            server.addConnector(connector);
            server.setHandler(context);

            log.info("Starting the server.");
            server.start();
            server.join();
        } catch (Error err) {
            log.error(err.getMessage(), err);
        } finally {
            server.destroy();
        }
    }

}
