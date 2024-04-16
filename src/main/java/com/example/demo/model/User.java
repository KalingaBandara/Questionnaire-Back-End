package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
//User class is to address the top level user field in JSON output for view profile api
public class User {
    @Getter
    @Setter
    private String firstname;
    @Getter
    @Setter
    private String lastname;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String nic;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    @Setter
    private String email;


}
