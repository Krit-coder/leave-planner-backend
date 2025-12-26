package com.krit.leave_planner_backend.repository;

import com.krit.leave_planner_backend.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByLeaveDateBetween(LocalDate start, LocalDate end);
    Optional<Leave> findByUserIdAndLeaveDate(Integer userId, LocalDate leaveDate);
}
//public interface LeaveRepository extends JpaRepository<Leave, Long> {
//}
