package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import com.example.demo.model.UserProfile;

@Service
public class UserProfileService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "http://20.15.114.131:8080/api/user/profile/view";

    public UserProfile getUserProfile(String jwtToken) {
        // Configure the RestTemplate with the JWT Request Interceptor
        JwtRequestInterceptor jwtRequestInterceptor = new JwtRequestInterceptor(jwtToken);
        restTemplate.getInterceptors().clear(); // Clear existing interceptors
        restTemplate.getInterceptors().add(jwtRequestInterceptor);

        try {
            // Make the GET request and parse the JSON response into the UserProfile model
            UserProfile userProfile = restTemplate.getForObject(API_URL, UserProfile.class);

            // Print the username in the terminal
            if (userProfile != null && userProfile.getUser() != null) {
                System.out.println("Fetched username: " + userProfile.getUser().getUsername());
            } else {
                System.err.println("Fetched user profile is null or has null user object");
            }

            return userProfile;
        } catch (RestClientException e) {
            // Log the error message to the console
            System.err.println("Error fetching user profile: " + e.getMessage());
            e.printStackTrace();
            // Return an empty UserProfile object or handle the exception as appropriate for your application
            return new UserProfile();
        }
    }
}
