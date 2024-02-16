Для начала я попробовал самостоятельно реализовать простой чат на основе сокетов 
Архитектура:

    Сервер:
        Принимает TCP-соединения от клиентов.
        При получении сообщения от клиента:
            Пересылает сообщение всем другим клиентам.
        При отключении клиента:
            Удаляет его из списка клиентов.
    Клиент:
        Подключается к серверу по TCP-соединению.
        Отправляет сообщения серверу.
        При получении сообщения от сервера:
            Выводит сообщение на экран.

![image](https://github.com/DmitryV1987/JavaJuniorFinal/assets/115727297/32fe3b01-f22c-4bd9-bdc3-e7361729dbb8)

(Вариант без личных сообщений в находится в попке NO PRIVATE)

Как добавить в него личные сообщения?

1. Добавление нового типа сообщения:

    Введем новый тип сообщения, например, PRIVATE_MESSAGE.
    Сообщение будет содержать:
        Идентификатор отправителя.
        Идентификатор получателя.
        Текст сообщения.

2. Изменение сервера:

    Сервер будет обрабатывать сообщения типа PRIVATE_MESSAGE:
        Проверять, что получатель онлайн.
        Пересылать сообщение только получателю.

3. Изменение клиента:

    Клиент будет иметь возможность:
        Отправлять личные сообщения другим пользователям.
        Отображать личные сообщения в отдельном окне.

Добавление нового типа сообщения:

В класс Message добавим новое поле type:

public class Message {

  private String type;
  private String senderId;
  private String receiverId;
  private String text;

  // ...

}

Введем константы для типов сообщений:

public static final String MESSAGE_TYPE_PUBLIC = "PUBLIC";
public static final String MESSAGE_TYPE_PRIVATE = "PRIVATE";

2. Изменение сервера:

    Добавим обработку сообщений типа PRIVATE_MESSAGE:

if (message.getType().equals(MESSAGE_TYPE_PRIVATE)) {
  // Проверить, что получатель онлайн
  if (clients.containsKey(message.getReceiverId())) {
    // Переслать сообщение только получателю
    clients.get(message.getReceiverId()).println(message);
  }
}

3. Изменение клиента:

    Добавим возможность отправлять личные сообщения:

System.out.println("Введите текст сообщения:");
String text = System.console().readLine();

Message message = new Message();
message.setType(MESSAGE_TYPE_PRIVATE);
message.setSenderId(userId);
message.setReceiverId(receiverId);
message.setText(text);

out.println(message);

    Добавим отображение личных сообщений:

while (true) {
  String messageJson = in.nextLine();
  Message message = Message.fromJson(messageJson);

  if (message.getType().equals(MESSAGE_TYPE_PUBLIC)) {
    // Отобразить публичное сообщение
  } else if (message.getType().equals(MESSAGE_TYPE_PRIVATE)) {
    // Отобразить личное сообщение
  }
}

Для сериализации/десериализации JSON можно использовать библиотеку Jackson: https://github.com/FasterXML/jackson.

(Вариант c личными сообщениямий в находится в папке PRIVATE)
