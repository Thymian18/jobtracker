package ch.thymian18.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(@NotBlank String username, @NotBlank String password) {
}
