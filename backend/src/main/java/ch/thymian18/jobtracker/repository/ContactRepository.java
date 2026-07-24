package ch.thymian18.jobtracker.repository;

import ch.thymian18.jobtracker.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
