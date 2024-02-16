/// Класс, представляющий сообщение

import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {

  // Тип сообщения (PUBLIC или PRIVATE)
  private String type;

  // ID отправителя
  private String senderId;

  // ID получателя (только для PRIVATE)
  private String receiverId;

  // Текст сообщения
  private String text;

  public static final String MESSAGE_TYPE_PUBLIC = "PUBLIC";
  public static final String MESSAGE_TYPE_PRIVATE = "PRIVATE";

  // Геттеры и сеттеры

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  // Сериализация/десериализация JSON

  public static Message fromJson(String json) {
    return new ObjectMapper().readValue(json, Message.class);
  }

  public String toJson() {
    return new ObjectMapper().writeValueAsString(this);
  }

}