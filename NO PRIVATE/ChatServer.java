import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {

  private static final int PORT = 8080;
  private static final List<PrintWriter> clients = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Server is listening on port " + PORT);

    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());

      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      clients.add(out);

      new Thread(() -> {
        try (InputStream in = clientSocket.getInputStream()) {
          byte[] buf = new byte[1024];
          while (true) {
            int bytesRead = in.read(buf);
            if (bytesRead == -1) {
              break;
            }

            String message = new String(buf, 0, bytesRead);
            System.out.println("Received message from " + clientSocket.getRemoteSocketAddress() + ": " + message);

            for (PrintWriter client : clients) {
              if (client != out) {
                client.println(message);
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          clients.remove(out);
          System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
        }
      }).start();
    }
  }
}