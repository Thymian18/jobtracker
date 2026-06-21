package ch.thymian18.jobtracker.service;

import ch.thymian18.jobtracker.dto.ContactRequestDto;
import ch.thymian18.jobtracker.dto.ContactResponseDto;
import ch.thymian18.jobtracker.entity.Company;
import ch.thymian18.jobtracker.entity.Contact;
import ch.thymian18.jobtracker.repository.CompanyRepository;
import ch.thymian18.jobtracker.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository, CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public List<ContactResponseDto> getAllContacts() {
        return contactRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    public ContactResponseDto getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));
        return toResponseDto(contact);
    }

    public ContactResponseDto createContact(ContactRequestDto dto) {
        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + dto.companyId()));

        Contact contact = new Contact();
        contact.setName(dto.name());
        contact.setRole(dto.role());
        contact.setEmail(dto.email());
        contact.setCompany(company);

        Contact saved = contactRepository.save(contact);
        return toResponseDto(saved);
    }

    public ContactResponseDto updateContact(Long id, ContactRequestDto dto) {
        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + dto.companyId()));
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));

        contact.setName(dto.name());
        contact.setRole(dto.role());
        contact.setEmail(dto.email());
        contact.setCompany(company);

        Contact updated = contactRepository.save(contact);
        return toResponseDto(updated);
    }

    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));

        contactRepository.delete(contact);
    }

    private ContactResponseDto toResponseDto(Contact contact) {
        return new ContactResponseDto(
                contact.getId(),
                contact.getName(),
                contact.getRole(),
                contact.getEmail(),
                contact.getCompany().getId(),
                contact.getCompany().getName()
        );
    }
}
