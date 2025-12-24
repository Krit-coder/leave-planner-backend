package com.krit.leave_planner_backend.repository;

import com.krit.leave_planner_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
