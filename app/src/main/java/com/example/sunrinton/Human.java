package com.example.sunrinton;

import java.util.Date;

/*
* 개인정보 저장 클래스
*/
public class Human {
    private String name;
    private Date birth;
    private String email;

    public Human(String name, Date birth, String email) {
        this.name = name;
        this.birth = birth;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
