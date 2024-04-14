package com.example.demo.dao;

import com.example.demo.model.HasAttempted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasAttemptedDao extends JpaRepository<HasAttempted, Long> {

    HasAttempted findByUserName(String username);
}

