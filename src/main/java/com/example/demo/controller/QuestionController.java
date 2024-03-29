package com.example.demo.controller;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Response;
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

    private static final String API_KEY = "NjVkNDIyMjNmMjc3NmU3OTI5MWJmZGI0OjY1ZDQyMjIzZjI3NzZlNzkyOTFiZmRhYQ";

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestionById(id);
    }

    @GetMapping("get")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestHeader HttpHeaders headers){
        if (headers.containsKey("API-Key") && headers.getFirst("API-Key").equals(API_KEY)) {
            return questionService.getQuestions();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


    @PostMapping("submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> responses){
        return questionService.calculateScore(responses);
    }

    @PostMapping("calculateScore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses){
        int score = questionService.calculateScore(responses).getBody(); // Get the calculated score from the service
        return ResponseEntity.ok(score); // Return the score in the response body
    }



}
