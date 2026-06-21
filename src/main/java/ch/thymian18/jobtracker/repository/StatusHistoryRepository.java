package ch.thymian18.jobtracker.repository;

import ch.thymian18.jobtracker.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
}
