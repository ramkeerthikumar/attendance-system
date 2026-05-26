package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.OvertimeEntry;
import net.javaguides.springboot.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OvertimeEntryRepository
        extends JpaRepository<OvertimeEntry, Long> {

    List<OvertimeEntry> findByWorkerAndDateBetween(
            Worker worker,
            LocalDate startDate,
            LocalDate endDate
    );
}