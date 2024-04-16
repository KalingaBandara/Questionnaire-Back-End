package com.example.demo.model;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

//Response is to calculate the score from the response containing selected option from frontend.
public class Response {
    private Integer questionId;
    private String response;
}
