package com.example.sunrinton;

public class Chat_Lobby {
    String chatName_Kor;
    String recent_chat;

    public Chat_Lobby(String key, String op_name, String recent_chat) {
    }

    public Chat_Lobby(String chatName_Kor, String recent_chat) {
        this.chatName_Kor = chatName_Kor;//사람 이름
        this.recent_chat = recent_chat;
    }


    public String getChatName_Kor() {
        return chatName_Kor;
    }

    public void setChatName_Kor(String chatName_Kor) {
        this.chatName_Kor = chatName_Kor;
    }

    public String getRecent_chat() {
        return recent_chat;
    }

    public void setRecent_chat(String recent_chat) {
        this.recent_chat = recent_chat;
    }
}
