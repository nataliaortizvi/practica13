package com.example.practica13;

public class Usuario {
    private String user, id, number;

    public Usuario() {
    }

    public Usuario(String user, String id, String number) {
        this.user = user;
        this.id = id;
        this.number = number;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

