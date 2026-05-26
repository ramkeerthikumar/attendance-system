package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}