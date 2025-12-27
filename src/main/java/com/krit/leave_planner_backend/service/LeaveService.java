package com.krit.leave_planner_backend.service;

import com.krit.leave_planner_backend.entity.Leave;
import com.krit.leave_planner_backend.repository.LeaveRepository;
import com.krit.leave_planner_backend.security.UserPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void toggleLeave(Integer userId, String dateStr) {

        // 🔐 Get logged-in user
        UserPrincipal currentUser =
                (UserPrincipal) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Long loggedInUserId = currentUser.getUserId();

        // ❌ Permission check
        if (!currentUser.isManager()
                && !loggedInUserId.equals(Long.valueOf(userId))) {

            throw new AccessDeniedException(
                    "You are not allowed to edit another user's calendar"
            );
        }

        // ✅ Business logic continues
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
        String currentType = leave.getType();

        switch (currentType) {
            case "P":
                leave.setType("1HF");
                leaveRepository.save(leave);
                break;

            case "1HF":
                leave.setType("2HF");
                leaveRepository.save(leave);
                break;

            case "2HF":
                leave.setType("U");
                leaveRepository.save(leave);
                break;

            case "U":
                leaveRepository.delete(leave);
                break;

//            default:
//                leaveRepository.delete(leave);
                }
    }

    public List<Leave> getLeaves(String startDate, String endDate) {
        return leaveRepository.findByLeaveDateBetween(
                LocalDate.parse(startDate),
                LocalDate.parse(endDate)
        );
    }
}
