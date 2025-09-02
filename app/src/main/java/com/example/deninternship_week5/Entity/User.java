package com.example.deninternship_week5.Entity;

public class User {
    private String uid;
    private String name;
    private String email;
    private String avatarUrl;

    public User() { }

    public User(String uid, String name, String email,String avatarUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.avatarUrl=avatarUrl;
    }

    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
