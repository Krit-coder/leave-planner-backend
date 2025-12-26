package com.krit.leave_planner_backend.service;

import com.krit.leave_planner_backend.entity.User;
import com.krit.leave_planner_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

}
