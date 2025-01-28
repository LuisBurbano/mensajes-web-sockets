package com.mensajeria.mensajes.controller;

import com.mensajeria.mensajes.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        return new Message(message.name(), message.content(), "MESSAGE");
    }

    @MessageMapping("/connect")
    public void connectUser(Message message) {
        Message connectMessage = Message.connected(message.name());
        messagingTemplate.convertAndSend("/topic/messages", connectMessage);
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(Message message) {
        Message disconnectMessage = Message.disconnected(message.name());
        messagingTemplate.convertAndSend("/topic/messages", disconnectMessage);
    }

    @MessageMapping("/private-message")
    public void sendPrivateMessage(Message message) {
        String recipient = message.name();  // Se espera que el campo 'name' contenga el nombre del destinatario
        messagingTemplate.convertAndSendToUser(recipient, "/queue/private",
                new Message(message.name(), message.content(), "PRIVATE"));
    }
}
