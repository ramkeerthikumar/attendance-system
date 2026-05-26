package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.OvertimeEntry;
import net.javaguides.springboot.repository.OvertimeEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/overtime")
public class OvertimeController {

    private final OvertimeEntryRepository overtimeEntryRepository;

    public OvertimeController(OvertimeEntryRepository overtimeEntryRepository) {
        this.overtimeEntryRepository = overtimeEntryRepository;
    }

    @PostMapping("/settle/{id}")
    public ResponseEntity<?> settleOvertime(@PathVariable Long id) {

        Optional<OvertimeEntry> optional = overtimeEntryRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Overtime not found");
        }

        OvertimeEntry entry = optional.get();

        entry.setSettlementStatus(
                net.javaguides.springboot.entity.SettlementStatus.SETTLED
        );

        overtimeEntryRepository.save(entry);

        return ResponseEntity.ok(entry);
    }
}