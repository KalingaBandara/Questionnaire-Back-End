package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HasAttempted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String jwtToken;
    private boolean hasAttempted;
    private int score;
    private String userName;

    public HasAttempted(String jwtToken,boolean hasAttempted, int score, String userName){
        this.jwtToken = jwtToken;
        this.hasAttempted = hasAttempted;
        this.score = score;
        this.userName = userName;
    }

    public HasAttempted() {

    }
}

