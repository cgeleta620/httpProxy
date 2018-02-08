package httpProxy;

import java.net.*;

/**
 * Created by cgeleta on 3/12/17.
 * This class is the main class that creates the threads for the proxy
 */
public class Proxy {

    public static void main(String[] args) {

        int port = 1996;// used for default
        port = Integer.parseInt(args[0]);// reads in from CL, first arg

        try {

            ServerSocket s = new ServerSocket(port);// listens to port
           // System.out.println("Port number: " + port);


            while (true) {// INF

               new HttpProxyClient(s.accept()).start();// new threads

            }


        } catch (Exception e) {
            System.out.println("Server Socket error");
            e.printStackTrace();

        }


    }// end main
}
