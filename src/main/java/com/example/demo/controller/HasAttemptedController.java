package com.example.demo.controller;

import com.example.demo.model.HasAttempted;
import com.example.demo.service.HasAttemptedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.UserProfileService;
import com.example.demo.model.UserProfile;
@RestController
@RequestMapping("/api")
public class HasAttemptedController {

    private final HasAttemptedService hasAttemptedService;
    private final UserProfileService userProfileService;

    @Autowired
    public HasAttemptedController(HasAttemptedService hasAttemptedService , UserProfileService userProfileService) {
        this.hasAttemptedService = hasAttemptedService;
        this.userProfileService = userProfileService;
    }



    @GetMapping("/updateAttemptStatus")
    public ResponseEntity<String> updateAttemptStatus(@RequestHeader HttpHeaders headers) {
        // Retrieve the JWT token from the specified header (e.g., "JWT-Token")
        String jwtToken = null;
        if (headers.containsKey("JWT-Token")) {
            jwtToken = headers.getFirst("JWT-Token");
        }

        // If JWT token is not present, return a bad request response
        if (jwtToken == null) {
            return new ResponseEntity<>("JWT token missing in the request headers", HttpStatus.BAD_REQUEST);
        }

        // Use the UserProfileService to fetch the user profile based on the JWT token
        UserProfile userProfile = userProfileService.getUserProfile(jwtToken);
        if (userProfile == null) {
            // Return unauthorized response if the user profile is not found
            return new ResponseEntity<>("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }

        // Retrieve the username from the UserProfile
        String username = userProfile.getUser().getUsername();
        HasAttempted userAttempt = new HasAttempted(jwtToken, true, -1, username);

        try {
            hasAttemptedService.saveOrUpdate(userAttempt);
            return new ResponseEntity<>("Attempt status updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update attempt status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserAttemptStatus")
    public ResponseEntity<Boolean> getUserAttemptStatus(@RequestHeader HttpHeaders headers) {
        // Retrieve the JWT token from the specified header (e.g., "JWT-Token")
        String jwtToken = null;
        if (headers.containsKey("JWT-Token")) {
            jwtToken = headers.getFirst("JWT-Token");
        }

        // If JWT token is not present, return a bad request response
        if (jwtToken == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        // Use the UserProfileService to fetch the user profile based on the JWT token
        UserProfile userProfile = userProfileService.getUserProfile(jwtToken);
        if (userProfile == null) {
            // Return unauthorized response if the user profile is not found
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }

        // Retrieve the username from the UserProfile
        String username = userProfile.getUser().getUsername();

        // Find the HasAttempted entity using the username
        HasAttempted userAttempt = hasAttemptedService.findByUserName(username);
        if (userAttempt != null) {
            return new ResponseEntity<>(userAttempt.isHasAttempted(), HttpStatus.OK);
        } else {
            // Return false if the HasAttempted entity does not exist
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

}
