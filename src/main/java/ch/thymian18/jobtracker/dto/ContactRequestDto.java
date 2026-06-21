package ch.thymian18.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

public record ContactRequestDto(@NotBlank String name, String role, @NotBlank String email, Long companyId) {
}
