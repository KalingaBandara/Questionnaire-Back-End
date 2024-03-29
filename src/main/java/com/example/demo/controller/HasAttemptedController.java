package com.example.demo.controller;

import com.example.demo.model.HasAttempted;
import com.example.demo.dao.HasAttemptedDao;
import com.example.demo.service.HasAttemptedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HasAttemptedController {

    private final HasAttemptedService hasAttemptedService;

    @Autowired
    public HasAttemptedController(HasAttemptedService hasAttemptedService) {
        this.hasAttemptedService = hasAttemptedService;
    }

    @PostMapping("/updateAttemptStatus")
    public ResponseEntity<String> updateAttemptStatus(@RequestBody HasAttempted hasAttempted) {
        try {
            hasAttemptedService.saveOrUpdate(hasAttempted);
            return new ResponseEntity<>("Attempt status updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update attempt status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserAttemptStatus")
    public ResponseEntity<Boolean> getUserAttemptStatus(@RequestParam String apiKey) {
        try {
            HasAttempted user = hasAttemptedService.findByApiKey(apiKey);
            if (user != null) {
                return new ResponseEntity<>(user.isHasAttempted(), HttpStatus.OK);
            } else {
                // Return false if the user does not exist
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
