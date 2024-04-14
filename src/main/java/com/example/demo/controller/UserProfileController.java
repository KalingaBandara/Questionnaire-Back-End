package com.example.demo.controller;

import com.example.demo.model.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;

@RestController
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;


    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(@RequestHeader HttpHeaders headers) {
        // Retrieve the JWT token from the "Authorization" header
        String jwtToken = null;
        if (headers.containsKey("Authorization")) {
            String authorizationHeader = headers.getFirst("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
            }
        }

        if (jwtToken == null) {
            // If the JWT token is missing, return an unauthorized status
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Use the UserProfileService to fetch the user profile
        UserProfile userProfile = userProfileService.getUserProfile(jwtToken);

        if (userProfile == null) {
            // If the user profile is null, return an internal server error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return the user profile as a response
        return ResponseEntity.ok(userProfile);
    }
}
