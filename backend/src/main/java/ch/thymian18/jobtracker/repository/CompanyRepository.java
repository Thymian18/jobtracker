package ch.thymian18.jobtracker.repository;

import ch.thymian18.jobtracker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
