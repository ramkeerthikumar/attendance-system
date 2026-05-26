package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.*;
import net.javaguides.springboot.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    private final AttendanceLogRepository attendanceLogRepository;
    private final WorkerRepository workerRepository;
    private final SiteRepository siteRepository;
    private final OvertimeEntryRepository overtimeEntryRepository;

    public AttendanceService(
            AttendanceLogRepository attendanceLogRepository,
            WorkerRepository workerRepository,
            SiteRepository siteRepository,
            OvertimeEntryRepository overtimeEntryRepository
    ) {
        this.attendanceLogRepository = attendanceLogRepository;
        this.workerRepository = workerRepository;
        this.siteRepository = siteRepository;
        this.overtimeEntryRepository = overtimeEntryRepository;
    }

    // ================= CLOCK IN =================
    @Transactional
    public AttendanceLog clockIn(Long workerId, Long siteId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new RuntimeException("Site not found"));

        AttendanceLog log = new AttendanceLog();
        log.setWorker(worker);
        log.setSite(site);
        log.setClockIn(LocalDateTime.now());
        log.setClockOut(null);
        log.setTotalHours(0.0);
        log.setOvertimeHours(0.0);
        log.setFlagged(false);

        return attendanceLogRepository.save(log);
    }

    // ================= CLOCK OUT =================
    @Transactional
    public AttendanceLog clockOut(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        AttendanceLog log = attendanceLogRepository
                .findTopByWorkerAndClockOutIsNullOrderByClockInDesc(worker)
                .orElseThrow(() -> new RuntimeException("No active clock-in found"));

        LocalDateTime in = log.getClockIn();
        LocalDateTime out = LocalDateTime.now();

        log.setClockOut(out);

        // HOURS CALCULATION
        double hours = Duration.between(in, out).toMinutes() / 60.0;
        log.setTotalHours(hours);

        // OVERTIME CALCULATION
        double overtime = Math.max(0, hours - 8);
        log.setOvertimeHours(overtime);

        if (hours > 16) {
            log.setFlagged(true);
        }

        AttendanceLog saved = attendanceLogRepository.save(log);

        // FLUSH (important for DB consistency)
        attendanceLogRepository.flush();

        // ================= OVERTIME ENTRY =================
        if (overtime > 0) {

            try {
                double ratePerHour = worker.getDailyWageRate() / 8.0;

                double amount = (overtime <= 2)
                        ? overtime * ratePerHour * 1.5
                        : (2 * ratePerHour * 1.5)
                        + ((overtime - 2) * ratePerHour * 2);

                OvertimeEntry entry = new OvertimeEntry();
                entry.setWorker(worker);
                entry.setAttendanceLog(saved); // MUST NOT BE NULL
                entry.setDate(LocalDate.now());
                entry.setOvertimeHours(overtime);
                entry.setOvertimeRateApplied(1.5);
                entry.setAmount(amount);
                entry.setSettlementStatus(SettlementStatus.PENDING);

                overtimeEntryRepository.save(entry);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Overtime insert failed: " + e.getMessage());
            }
        }

        return saved;
    }
}