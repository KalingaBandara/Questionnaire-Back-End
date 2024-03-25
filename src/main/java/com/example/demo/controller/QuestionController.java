package com.example.demo.controller;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Response;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3008")
@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

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
    public ResponseEntity<List<QuestionWrapper>> getQuestions(){
        return questionService.getQuestions();
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
