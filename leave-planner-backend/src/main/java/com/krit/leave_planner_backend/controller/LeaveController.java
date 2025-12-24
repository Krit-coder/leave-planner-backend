package com.krit.leave_planner_backend.controller;

import com.krit.leave_planner_backend.dto.LeaveRequest;
import com.krit.leave_planner_backend.entity.Leave;
import com.krit.leave_planner_backend.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // ✅ TOGGLE LEAVE
    @PostMapping
    public void toggleLeave(@RequestBody LeaveRequest request) {
        leaveService.toggleLeave(
                request.getUserId(),
                request.getDate()
        );
    }

    // ✅ LOAD LEAVES FOR CALENDAR (MONTH VIEW)
    @GetMapping
    public List<Leave> getLeaves(
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return leaveService.getLeaves(startDate, endDate);
    }
}
