package com.example.demo.model;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class Response {
    private Integer questionId;
    private String response;
}
