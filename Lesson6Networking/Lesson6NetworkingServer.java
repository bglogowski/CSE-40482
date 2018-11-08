import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Lesson6NetworkingServer {

    // Set some default values for these variables
    private static Integer port = 8080;
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static PrintWriter outputStream = null;

    public static void main(String[] args) {
        
        // Parse command line arguments
        String arg;
        int i = 0;
        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];
            if (arg.endsWith("port"))
                port = Integer.parseInt(args[i++]);
        }

        // Bind the server socket to a port
        try {
            server = new ServerSocket(port);
            System.out.println("Listening on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not bind to port.");
            System.err.println("Exiting...");
            System.exit(1);
        }
        
        // Create a loop to listen for connections
        while (true) {

            try {

                // Accept new connections
                socket = server.accept();
                outputStream = new PrintWriter(socket.getOutputStream());

                // Send data to the client
                outputStream.print("HTTP/1.1 200\r\n");
                outputStream.print("Content-Type: text/html\r\n\r\n\r\n");
                outputStream.print("<html>\r\n" + 
                        "<head><title>Java Networking</title></head>\r\n" + 
                        "<body>\r\n" + 
                        "<h1>Java Networking</h1>\r\n" + 
                        "</body>\r\n" + 
                        "</html>\r\n");

                outputStream.flush();
                
                // Close the connection to the client
                outputStream.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("I/O error.");
                System.err.println("Exiting...");
                System.exit(1);
            }

        }

    }

}
