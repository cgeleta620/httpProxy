package httpProxy;

import java.io.*;
import java.net.*;

/**
 * Created by cgeleta on 3/12/17.
 * This class extends thread to create a concurrent http proxy.
 * This class connects to a server, grabs content, and then sends it back to the host
 */
public class HttpProxyClient extends Thread {

    private Socket connectionSocket = null;
    private String host;// the host
    private int port; // the port

    public HttpProxyClient(Socket theSocket) {
        super("HttpProxyClient");
        this.connectionSocket = theSocket;
    }

    public void run()// ran when a new thread is made
    {

        String clientSentence = "";
        Socket socket = null;// the connection
        BufferedReader inFromClient = null; // to get request
        DataOutputStream outToClient = null;// to write to webpage
        DataInputStream hostIn = null;// get html
        PrintStream hostOut = null;// send the request to server
        Parse parse = null;// parse object

        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();// gets the request
           // System.out.println("Received: " + clientSentence);


            parse = new Parse(clientSentence);//parses the request
            this.host = parse.getDomain(); // saves domain to data member
            this.port = parse.getPort(); // saves port number to the data member
            String parsedString = parse.toString(); // gets parsed string
         //   System.out.println(parsedString);

            Socket hostSocket = new Socket(this.host, this.port);// connects to socket
            hostOut = new PrintStream(hostSocket.getOutputStream());//  to write to server
            hostIn = new DataInputStream(hostSocket.getInputStream());// to read from host

            hostOut.print(parsedString);// sends request to the server

        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {

            while (true) {// inf until EOF

                if(parse.isFlag()) {// if this flag is set then there is an error
                    outToClient.writeBytes(parse.getError());// outputs error as html


                }
                outToClient.writeByte((char) hostIn.readByte());// outputs the html ie. webpage
             //   outToClient.flush();
               // outToClient.writeChar('*');

            }
        } catch (EOFException e) {// end of file exception
            //this is here
        } catch(IOException e1){
            // this is also here
        } catch (NullPointerException e2){
            //this is here again
        }


    }


}// end of class


