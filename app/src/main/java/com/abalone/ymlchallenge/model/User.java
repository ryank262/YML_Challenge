package com.abalone.ymlchallenge.model;

/* Class representing a Git User's information */
public class User {

    private String username;
    private String full_name;
    private int followers;
    private int following;
    private int repositories;
    private String bio;
    private String company;
    private String location;
    private String email;
    private String avatar_url;

    public User(String username, String full_name, int followers, int following, int repositories, String bio, String company, String location, String email, String avatar_url) {
        this.username = username;
        this.full_name = full_name;
        this.followers = followers;
        this.following = following;
        this.repositories = repositories;
        this.bio = bio;
        this.company = company;
        this.location = location;
        this.email = email;
        this.avatar_url = avatar_url;
    }

    public String getUsername() {
        return username;
    }

    public String getFull_name() {
        return full_name;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getRepositories() {
        return repositories;
    }

    public String getBio() {
        return bio;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
