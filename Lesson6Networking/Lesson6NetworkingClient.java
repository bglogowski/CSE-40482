import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Lesson6NetworkingClient {

    // Set some default values for these variables
    private static Integer port = 8080;
    private static String server = "localhost";

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        // Parse command line arguments
        String arg;
        int i = 0;
        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];
            if (arg.endsWith("port"))
                port = Integer.parseInt(args[i++]);
            if (arg.endsWith("server"))
                server = args[i++];
        }

        // Open socket connection to server
        Socket socket = new Socket(server, port);
        
        // Create a scanner to read the input stream
        Scanner scanner = new Scanner(socket.getInputStream(), "UTF-8");

        // Print out the data received from the server
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        // Close the connection to the server
        socket.close();

    }

}
