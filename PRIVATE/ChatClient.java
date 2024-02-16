// Клиент чата

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

  private static final String HOST = "localhost";
  private static final int PORT = 8080;

  public static void main(String[] args) throws IOException {
  // Подключение к серверу
    Socket clientSocket = new Socket(HOST, PORT);
    System.out.println("Подключен к серверу: " + clientSocket.getRemoteSocketAddress());

// Потоки ввода/вывода
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    Scanner in = new Scanner(clientSocket.getInputStream());
	
	// Поток для отправки сообщений
    new Thread(() -> {
      while (true) {
	  // Ввод текста сообщения
        String messageText = System.console().readLine();
        if (messageText.isEmpty()) {
          continue;
        }

		// Создание объекта сообщения
        Message message = new Message();
        message.setType(Message.MESSAGE_TYPE_PUBLIC);
        message.setText(messageText);

		// Отправка сообщения серверу
        out.println(message.toJson());
      }
    }).start();

	// Поток для получения сообщений
    while (true) {
		// Получение JSON-строки сообщения
      String messageJson = in.nextLine();
	  
	  // Преобразование JSON-строки в объект сообщения
      Message message = Message.fromJson(messageJson);

      // Отображение сообщений
      if (message.getType().equals(Message.MESSAGE_TYPE_PUBLIC)) {
        System.out.println("[PUBLIC] " + message.getText());
      } else if (message.getType().equals(Message.MESSAGE_TYPE_PRIVATE)) {
        System.out.println("[PRIVATE] " + message.getText());
      }
    }
  }
}