package com.example.jdbctemplate.model;

public class User {
    private int id;
    private String login;
    private String password;
    private String email;
    private String creationDate;

    public User(int id, String login, String password, String email, String creationDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}