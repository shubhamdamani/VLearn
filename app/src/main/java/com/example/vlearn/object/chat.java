package com.example.vlearn.object;

public class chat {
    private String message;
    private String sender;
    private String reciever;

    public chat(String message, String sender, String reciever) {
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
    }

    public chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }
}
