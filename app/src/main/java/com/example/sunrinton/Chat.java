package com.example.sunrinton;

public class Chat {
    private String sender;
    private String message;
    private boolean prev;
    public Chat() {
    }



    public Chat(String message, String sender,boolean prev) {
        this.sender = sender;
        this.message = message;
        this.prev=prev;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}