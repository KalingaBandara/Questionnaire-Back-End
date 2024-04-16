package com.example.demo.controller;
import com.example.demo.model.*;
import com.example.demo.service.HasAttemptedService;
import com.example.demo.service.UserProfileService;

import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    HasAttemptedService hasAttemptedService;

    @Autowired
    private UserProfileService userProfileService;



    @GetMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestHeader HttpHeaders headers) {
        // Use the UserProfileService to validate and retrieve the user profile
        ResponseEntity<UserProfile> responseEntity = userProfileService.validateAndRetrieveUserProfile(headers);

        // Check if the response is not successful
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            // Return the status code from response entity as the response
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        }

        // Retrieve the user profile from the response entity
        UserProfile userProfile = responseEntity.getBody();

        // Retrieve the username from the UserProfile
        String username = userProfile.getUser().getUsername();

        // Check if the user has attempted using the username
        HasAttempted user = hasAttemptedService.findByUserName(username);
        if (user != null) {
            // Return unauthorized response if the user has attempted
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // If all verifications pass, fetch the questions
        return questionService.getQuestionsWithoutAnswers();
    }

    @PostMapping("/calculateScore")
    public ResponseEntity<Integer> calculateScore(@RequestHeader HttpHeaders headers, @RequestBody List<Response> responses) {
        // Use the UserProfileService to validate and retrieve the user profile
        ResponseEntity<UserProfile> responseEntity = userProfileService.validateAndRetrieveUserProfile(headers);

        // Check if the response is not successful
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            // Return the response code and zero score
            return ResponseEntity.status(responseEntity.getStatusCode()).body(0);
        }

        // Retrieve the user profile from the response entity
        UserProfile userProfile = responseEntity.getBody();

        // Retrieve the username from the UserProfile
        String username = userProfile.getUser().getUsername();

        // Calculate the score using the question service
        ResponseEntity<Integer> scoreResponse = questionService.calculateScore(responses);
        int score = scoreResponse.getBody();

        // Check the response status
        if (scoreResponse.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().body(score);
        }

        // Update the user's score and attempt status
        HasAttempted user = hasAttemptedService.findByUserName(username);
        if (user != null) {
            user.setScore(score);
            user.setHasAttempted(true);
            hasAttemptedService.saveOrUpdate(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized access
        }

        // Return the calculated score
        return ResponseEntity.ok(score);
    }


    @GetMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestHeader HttpHeaders headers) {
        // Use the UserProfileService to validate and retrieve the user profile
        ResponseEntity<UserProfile> responseEntity = userProfileService.validateAndRetrieveUserProfile(headers);

        // Check if the response is not successful
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            // Return the response code and zero score
            return ResponseEntity.status(responseEntity.getStatusCode()).body(0);
        }

        // Retrieve the user profile from the response entity
        UserProfile userProfile = responseEntity.getBody();

        // Retrieve the username from the UserProfile
        String username = userProfile.getUser().getUsername();

        // Fetch the user's attempted status and score using the username
        HasAttempted user = hasAttemptedService.findByUserName(username);
        if (user != null) {
            return ResponseEntity.ok(user.getScore());
        } else {
            return ResponseEntity.notFound().build(); // User not found for the given username
        }
    }
}





