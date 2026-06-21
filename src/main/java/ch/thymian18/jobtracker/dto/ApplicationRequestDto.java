package ch.thymian18.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ApplicationRequestDto(@NotBlank String position, LocalDate appliedDate, String jobPostingText, Long companyId) {
}
