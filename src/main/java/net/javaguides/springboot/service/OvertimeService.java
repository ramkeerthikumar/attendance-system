package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.OvertimeEntry;
import net.javaguides.springboot.entity.Worker;
import net.javaguides.springboot.repository.OvertimeEntryRepository;
import net.javaguides.springboot.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OvertimeService {

    private final OvertimeEntryRepository overtimeEntryRepository;
    private final WorkerRepository workerRepository;

    public OvertimeService(
            OvertimeEntryRepository overtimeEntryRepository,
            WorkerRepository workerRepository
    ) {
        this.overtimeEntryRepository = overtimeEntryRepository;
        this.workerRepository = workerRepository;
    }

    public Map<String, Object> getMonthlySummary(Long workerId, String month) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        YearMonth yearMonth = YearMonth.parse(month);

        List<OvertimeEntry> entries =
                overtimeEntryRepository.findByWorkerAndDateBetween(
                        worker,
                        yearMonth.atDay(1),
                        yearMonth.atEndOfMonth()
                );

        double totalHours = entries.stream()
                .mapToDouble(OvertimeEntry::getOvertimeHours)
                .sum();

        double totalAmount = entries.stream()
                .mapToDouble(OvertimeEntry::getAmount)
                .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("worker", worker.getName());
        response.put("month", month);
        response.put("totalOvertimeHours", totalHours);
        response.put("totalAmount", totalAmount);
        response.put("entries", entries);

        return response;
    }
}