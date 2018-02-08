package httpProxy;
 /**
  * Author: Chris Geleta
  * Class: CS457 Networks - Isaacman
  * This is the parse class. This is used to parse a http request and return a header.
 */
public class Parse {

    private String request;// The whole request line
    private String location = "/";// the path variable ie where it is located
    private String method; // GET/POST etc...
    private String version; // HTTP/1.0
    private String domain; //www.google.com
    private String URL; // http://www.google.com:80/index.html
    private String theRest = ""; // anything left over after the version
    private int port; // the port number 80 is default
    private boolean flag = false;
    private String error;

    /**
     * This method parses a(n) URL into domain, port, and path.
     */
    private void tokenize() {

        String url = getURL();

        int pos = url.indexOf(":", 5);// skips the http
        boolean bool;
        boolean bool_two;

        if (pos != -1) {// returns -1 if it doesn't have the char ":"

            String check = url.substring(pos);

            char str_check = check.charAt(1);
            char str_check_two = check.charAt(2);

            bool = Character.isDigit(str_check);
            bool_two = Character.isDigit(str_check_two);

            if (bool && bool_two) {

                String partialDomain = url.substring(0, pos);// gets the domain with the http


                if (partialDomain.contains("://")) {
                    String[] dArr = partialDomain.split("://");// removes http://
                    setDomain(dArr[1]);
                }

                String p = url.substring(pos);// the domain and path combined

                int posEndPort = p.indexOf("/");
                String port = p.substring(1, posEndPort);// substring of the port #
                String path = p.substring(posEndPort);// substring of location

                setPort(Integer.parseInt(port));// makes the string port a int
                setLocation(path);

            }
        }


        String[] splitArr = url.split("://"); // gets rid of http://
        int slashPos = splitArr[1].indexOf("/");// finds where path starts
        String d = splitArr[1].substring(0, slashPos);// substring makes domain
        String[] d_arr = d.split(":");
        String domain = d_arr[0];
        String path = splitArr[1].substring(slashPos); // substring makes location

        setPort(80);// default port 80 if not included
        setDomain(domain);
        setLocation(path);


    }

    public Parse(String request) {
        this.request = request; // request from stdin

     //   System.out.println("HERE: " + this.getDomain());


        if(request.charAt(4) == '/') {

            this.domain = "localhost";
            this.method = "GET";
            this.port = 80;
            int pos = request.indexOf("HTTP/1.");
            this.location = request.substring(4,pos-1);
            this.version = "HTTP/1.0";

        }

        else {

            if(!request.toLowerCase().contains("get")) {
                this.flag = true;
                this.error = "<html><h1>Must be a GET request, 405</h1</html>";
            }

            else if(!request.contains("/1.0")) {
                this.flag = true;
                this.error = "<html><h1>Must be a http/1.0</h1</html>";

            }
            else if (!(request.length() > 9)) {
                this.flag = true;
                this.error = "<html><h1>Error: 400 </h1</html>";
            }

            else {


                //    System.out.println("here");


                String[] split = getRequest().split(" ");// splits up method, URL, and version
                setMethod(split[0]);// gets method
                setURL(split[1]);// gets URL
                setVersion(split[2]);// get the version


                String other = "";

                if (split.length > 3) {

                    for (int q = 3; q < split.length; q++) { // starts at where the version ended goes until end
                        other += split[q] + " "; // collects the rest after version
                    }
                    other += "\r\n";

                    setTheRest(other);// saves the rest in case it is needed later
                }

                tokenize();// Splits the URL
            }
        }


    }

    /**
     * This creates the header in string format
     *
     * @return the formatted header
     */
    public String toString() {

        return getMethod() + " " + getLocation() + " " + getVersion() + "\n" +
                "Host: " + getDomain() + "\n" + "Connection: " + getConnection() + "\n" + getTheRest() + "\r\n";

    }

    public String getConnection() {
        String connection = "close";
        return connection;
    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTheRest() {
        return theRest;
    }

    public void setTheRest(String theRest) {
        this.theRest = theRest;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
