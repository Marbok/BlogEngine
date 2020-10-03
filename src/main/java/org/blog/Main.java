package org.blog;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar(location.toExternalForm());

        server.setHandler(webapp);
        server.start();
        server.join();
    }
}
