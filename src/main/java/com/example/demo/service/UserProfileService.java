package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import com.example.demo.model.UserProfile;

@Service
public class UserProfileService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "http://20.15.114.131:8080/api/user/profile/view";

    // Method to validate JWT token and retrieve the user profile
    public ResponseEntity<UserProfile> validateAndRetrieveUserProfile(HttpHeaders headers) {
        // Retrieve the JWT token from the specified header (e.g., "JWT-Token")
        String jwtToken = getJwtToken(headers);

        // If JWT token is not present, return a bad request response
        if (jwtToken == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // Configure the RestTemplate with the JWT Request Interceptor
        JwtRequestInterceptor jwtRequestInterceptor = new JwtRequestInterceptor(jwtToken);
        restTemplate.getInterceptors().clear(); // Clear existing interceptors
        restTemplate.getInterceptors().add(jwtRequestInterceptor);

        try {
            // Make the GET request and parse the JSON response into the UserProfile model
            UserProfile userProfile = restTemplate.getForObject(API_URL, UserProfile.class);

            // Check if the user profile is valid
            if (userProfile == null || userProfile.getUser() == null) {
                // Return unauthorized response if the user profile is not found or invalid
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            // Return the user profile with OK status
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (RestClientException e) {
            // Log the error message to the console
            System.err.println("Error fetching user profile: " + e.getMessage());
            e.printStackTrace();
            // Return an internal server error response if there was an exception during the request
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to retrieve JWT token from HttpHeaders
    private String getJwtToken(HttpHeaders headers) {
        if (headers.containsKey("JWT-Token")) {
            return headers.getFirst("JWT-Token");
        }
        return null;
    }

}
