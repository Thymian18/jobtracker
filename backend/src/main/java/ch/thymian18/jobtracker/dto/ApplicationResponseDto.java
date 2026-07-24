package ch.thymian18.jobtracker.dto;

import ch.thymian18.jobtracker.entity.ApplicationStatus;

import java.time.LocalDate;
import java.util.List;

public record ApplicationResponseDto(Long id, String position, ApplicationStatus status, LocalDate appliedDate, String jobPostingText, Long companyId, String companyName, List<StatusHistoryResponseDto> statusHistory) {
}
