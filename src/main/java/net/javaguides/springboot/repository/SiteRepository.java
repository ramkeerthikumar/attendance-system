package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}