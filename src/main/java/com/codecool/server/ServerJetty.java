package com.codecool.server;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerJetty {

    private Server server;

    public void start() throws Exception {
        final Server server = new Server();
        final HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSecureScheme("https");
        httpConfiguration.setSecurePort(8443);


        final SslContextFactory sslContextFactory = new SslContextFactory("src/mykey.jks");
        sslContextFactory.setKeyStorePassword("katamaran29");

        final HttpConfiguration httpsConfiguration = new HttpConfiguration(httpConfiguration);
        httpsConfiguration.addCustomizer(new SecureRequestCustomizer());

        final ServerConnector httpsConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(httpsConfiguration));

        httpsConnector.setPort(8443);
        server.addConnector(httpsConnector);

        ContextHandlerCollection contexts = new ContextHandlerCollection();



        ServletHolder mainHolder = new ServletHolder(new BlockingServlet() {

            private static final long serialVersionUID = 1L;

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                resp.getWriter().write("main");
            }
        });

        WebAppContext webAppContext = new WebAppContext("test.war", "/home/taras/IdeaProjects/webshop-java-warz/src/main/java/com/codecool/server/BlockingServlet.java");
        webAppContext.addServlet(mainHolder, "/main");

        server.setHandler(webAppContext);

        server.start();
        server.join();



    }

    public static void main(String[] args) throws Exception {
        ServerJetty serverJetty = new ServerJetty();
        serverJetty.start();

//        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
//        serverJetty.start();
    }
}
