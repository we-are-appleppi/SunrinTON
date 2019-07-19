package com.example.sunrinton;

public class Lobby_datas {
    String name, preview;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Lobby_datas(String name, String preview) {
        this.name = name;
        this.preview = preview;
    }
}
