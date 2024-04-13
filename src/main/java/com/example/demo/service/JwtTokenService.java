package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtTokenService {
    // Secret key used for signing the JWT
    private static final String SECRET_KEY = "Tesseract";

    // Method to create a JWT token from a username
    public String createToken(String username) {
        // Define the expiration time (e.g., 1 hour from now)
        long expirationTime = System.currentTimeMillis() + 60 * 60 * 1000;

        // Create the JWT token
        return Jwts.builder()
                .setSubject(username) // Set the subject (username) of the token
                .setIssuedAt(new Date()) // Set the issued-at time
                .setExpiration(new Date(expirationTime)) // Set the expiration time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with HS256 algorithm and secret key
                .compact();
    }
}

