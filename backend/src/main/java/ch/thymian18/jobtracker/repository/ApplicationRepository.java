package ch.thymian18.jobtracker.repository;

import ch.thymian18.jobtracker.entity.Application;
import ch.thymian18.jobtracker.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Kommunikationskette: Aussenwelt (HTTP/JSON)  →  DTO  →  Service  →  Entity  →  Repository  →  Datenbank (SQL/Tabellen)
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCompanyId(Long companyId);
    List<Application> findByStatus(ApplicationStatus status);
}
