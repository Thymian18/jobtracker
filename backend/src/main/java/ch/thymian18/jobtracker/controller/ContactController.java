package ch.thymian18.jobtracker.controller;

import ch.thymian18.jobtracker.dto.ContactRequestDto;
import ch.thymian18.jobtracker.dto.ContactResponseDto;
import ch.thymian18.jobtracker.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<ContactResponseDto> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ContactResponseDto getContactById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody @Valid ContactRequestDto requestDto) {
        ContactResponseDto created = contactService.createContact(requestDto);
        URI location = URI.create("/contacts/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ContactResponseDto updateContact(@PathVariable Long id, @RequestBody @Valid ContactRequestDto requestDto) {
        return contactService.updateContact(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
