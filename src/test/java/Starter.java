

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class Starter {

    public static final int    PORT             = 9900;

    public static final String CONTEXT          = "/";


    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        server.setHandler(new WebAppContext("src/main/webapp", CONTEXT));
        server.setStopAtShutdown(true);
        server.start();

        System.out.println("Hit Enter in console to stop server");
        if (System.in.read() != 0) {
            System.out.println("Server stopping");
            server.stop();
            System.out.println("Server stopped");
        }
    }

}
