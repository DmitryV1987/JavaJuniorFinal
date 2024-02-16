import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatClient {

  private static final String HOST = "localhost";
  private static final int PORT = 8080;

  public static void main(String[] args) throws IOException {
    Socket clientSocket = new Socket(HOST, PORT);
    System.out.println("Connected to server: " + clientSocket.getRemoteSocketAddress());

    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    Scanner in = new Scanner(clientSocket.getInputStream());

    new Thread(() -> {
      while (true) {
        String message = in.nextLine();
        System.out.println("Received message: " + message);
      }
    }).start();

    while (true) {
      String message = System.console().readLine();
      out.println(message);
    }
  }
}