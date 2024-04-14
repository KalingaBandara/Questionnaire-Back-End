package com.example.demo.service;

import com.example.demo.dao.HasAttemptedDao;
import com.example.demo.model.HasAttempted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HasAttemptedService {

    private final HasAttemptedDao hasAttemptedDao;

    @Autowired
    public HasAttemptedService(HasAttemptedDao hasAttemptedDao) {
        this.hasAttemptedDao = hasAttemptedDao;
    }

    public HasAttempted findByUserName(String username) {
        return hasAttemptedDao.findByUserName(username);
    }

    public HasAttempted saveOrUpdate(HasAttempted hasAttempted) {
        return hasAttemptedDao.save(hasAttempted);
    }
}
