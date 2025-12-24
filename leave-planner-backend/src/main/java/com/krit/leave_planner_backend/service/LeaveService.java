package com.krit.leave_planner_backend.service;

import com.krit.leave_planner_backend.entity.Leave;
import com.krit.leave_planner_backend.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public void toggleLeave(Long userId, String dateStr) {

        LocalDate date = LocalDate.parse(dateStr);

        Optional<Leave> existing =
                leaveRepository.findByUserIdAndLeaveDate(userId, date);

        if (existing.isEmpty()) {
            // EMPTY → P
            Leave leave = new Leave();
            leave.setUserId(userId);
            leave.setLeaveDate(date);
            leave.setType("P");
            leaveRepository.save(leave);
            return;
        }

        Leave leave = existing.get();

        if ("P".equals(leave.getType())) {
            // P → U
            leave.setType("U");
            leaveRepository.save(leave);
        } else {
            // U → EMPTY
            leaveRepository.delete(leave);
        }
    }
    public List<Leave> getLeaves(String startDate, String endDate) {
        return leaveRepository.findByLeaveDateBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}
