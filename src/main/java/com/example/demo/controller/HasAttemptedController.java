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

        // Use the UserProfileService to validate and retrieve the user profile
        ResponseEntity<UserProfile> responseEntity = userProfileService.validateAndRetrieveUserProfile(headers);
        // Check if the response is not successful
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            // Return a response with the status code and reason phrase
            return new ResponseEntity<>(responseEntity.getStatusCode().toString(), responseEntity.getStatusCode());
        }

        // Retrieve the user profile and username
        UserProfile userProfile = responseEntity.getBody();
        String username = userProfile.getUser().getUsername();

        // Extract the JWT token from headers
        String jwtToken = headers.getFirst("JWT-Token");

        // Create the HasAttempted object
        HasAttempted userAttempt = new HasAttempted(jwtToken, true, -1, username);

        try {
            hasAttemptedService.saveOrUpdate(userAttempt);
            return new ResponseEntity<>("Attempt status updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update attempt status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserAttemptStatus")
    public ResponseEntity<Boolean> getUserAttemptStatus(@RequestHeader HttpHeaders headers) {
        // Use the UserProfileService to validate and retrieve the user profile
        ResponseEntity<UserProfile> responseEntity = userProfileService.validateAndRetrieveUserProfile(headers);

        // Check if the response is not successful
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            // Return a response with the status code and reason phrase
            return new ResponseEntity<>(false, responseEntity.getStatusCode());
        }

        // Retrieve the user profile from the response
        UserProfile userProfile = responseEntity.getBody();

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
