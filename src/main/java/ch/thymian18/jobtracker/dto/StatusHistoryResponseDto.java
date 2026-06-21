package ch.thymian18.jobtracker.dto;

import ch.thymian18.jobtracker.entity.ApplicationStatus;

import java.time.LocalDateTime;

public record StatusHistoryResponseDto(Long id, ApplicationStatus status, LocalDateTime changedAt) {
}
