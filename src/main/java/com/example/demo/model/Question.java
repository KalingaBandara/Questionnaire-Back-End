package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String feedback1;
    private String feedback2;
    private String feedback3;
    private String feedback4;
    private String generalFeedback;
    private String rightAnswerIndex;

}
