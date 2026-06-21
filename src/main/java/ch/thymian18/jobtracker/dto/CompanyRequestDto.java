package ch.thymian18.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

// Request-DTO: was der Client beim Erstellen/Updaten schickt
public record CompanyRequestDto(@NotBlank String name, String website) {
}
