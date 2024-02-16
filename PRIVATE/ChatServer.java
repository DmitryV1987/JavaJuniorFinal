// Сервер чата

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {

  private static final int PORT = 8080;

  // Список клиентов (сокет -> PrintWriter)
  private static final Map<String, PrintWriter> clients = new HashMap<>();

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Сервер запущен на порту " + PORT);

    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Новый клиент подключен: " + clientSocket.getRemoteSocketAddress());

      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      clients.put(clientSocket.getRemoteSocketAddress().toString(), out);

      new Thread(() -> {
        try (InputStream in = clientSocket.getInputStream()) {
          byte[] buf = new byte[1024];
          while (true) {
            int bytesRead = in.read(buf);
            if (bytesRead == -1) {
              break;
            }

            String messageJson = new String(buf, 0, bytesRead);
            Message message = Message.fromJson(messageJson);

            // Обработка сообщений
            if (message.getType().equals(Message.MESSAGE_TYPE_PUBLIC)) {
              // Отправить всем сообщение
              for (PrintWriter client : clients.values()) {
                client.println(messageJson);
              }
            } else if (message.getType().equals(Message.MESSAGE_TYPE_PRIVATE)) {
              // Проверить, что получатель онлайн
              if (clients.containsKey(message.getReceiverId())) {
                // Отправить сообщение получателю
                clients.get(message.getReceiverId()).println(messageJson);
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          clients.remove(clientSocket.getRemoteSocketAddress().toString());
          System.out.println("Клиент отключен: " + clientSocket.getRemoteSocketAddress());
        }
      }).start();
    }
  }
}