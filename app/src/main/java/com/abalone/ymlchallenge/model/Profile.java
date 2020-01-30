package com.abalone.ymlchallenge.model;

/* Class representing a github follower */
public class Profile {

    private String avatarUrl;
    private String username;

    public Profile(String avatarUrl, String username) {
        this.avatarUrl = avatarUrl;
        this.username = username;
    }

    public String getAvatarUrl() { return avatarUrl; }

    public String getUsername() { return username; }
}
