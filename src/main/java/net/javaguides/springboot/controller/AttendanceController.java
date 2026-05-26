package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ClockInRequest;
import net.javaguides.springboot.entity.AttendanceLog;
import net.javaguides.springboot.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ================= CLOCK IN =================
    @PostMapping("/clock-in")
    public ResponseEntity<AttendanceLog> clockIn(@RequestBody ClockInRequest request) {

        AttendanceLog result = attendanceService.clockIn(
                request.getWorkerId(),
                request.getSiteId()
        );

        return ResponseEntity.ok(result);
    }

    // ================= CLOCK OUT =================
    @PostMapping("/clock-out")
    public ResponseEntity<AttendanceLog> clockOut(@RequestBody ClockInRequest request) {

        AttendanceLog result = attendanceService.clockOut(
                request.getWorkerId()
        );

        return ResponseEntity.ok(result);
    }
}