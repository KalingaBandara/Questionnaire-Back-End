package com.example.demo.controller;

import com.example.demo.model.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;

@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile() {
        // Use the service to fetch the user profile
        UserProfile userProfile = userProfileService.getUserProfile();

        // Return the user profile as a response
        return ResponseEntity.ok(userProfile);
    }
}
