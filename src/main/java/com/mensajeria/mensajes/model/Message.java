package com.mensajeria.mensajes.model;

public record Message(String name, String content, String type) {

    public static Message connected(String name) {
        return new Message(name, "se ha conectado", "CONNECT");
    }

    public static Message disconnected(String name) {
        return new Message(name, "se ha desconectado", "DISCONNECT");
    }
}
