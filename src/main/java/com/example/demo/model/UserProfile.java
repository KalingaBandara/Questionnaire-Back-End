package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UserProfile {
    @JsonProperty("user")
    private User user;

    // Getters and setters
    public User getUser() {
        return user;
    }
}



