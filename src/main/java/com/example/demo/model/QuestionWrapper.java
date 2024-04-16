package com.example.demo.model;
import lombok.Data;
@Data
public class QuestionWrapper {
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

    //QuestionWrapper is to fetch the fields in the database to the frontend except the right answer.
    public QuestionWrapper(Integer questionId, String questionTitle, String option1, String option2, String option3, String option4, String feedback1, String feedback2, String feedback3, String feedback4, String generalFeedback) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.feedback1 = feedback1;
        this.feedback2 = feedback2;
        this.feedback3 = feedback3;
        this.feedback4 = feedback4;
        this.generalFeedback = generalFeedback;

    }
}
