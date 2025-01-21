package com.mensajeria.mensajes.controller;

import com.mensajeria.mensajes.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message){

        return new Message(message.name(), message.content());
    }
}
