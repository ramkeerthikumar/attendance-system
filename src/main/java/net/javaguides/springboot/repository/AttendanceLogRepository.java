package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.AttendanceLog;
import net.javaguides.springboot.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {

    // ✅ BEST PRACTICE: always fetch latest active clock-in safely
    Optional<AttendanceLog> findTopByWorkerAndClockOutIsNullOrderByClockInDesc(Worker worker);

}