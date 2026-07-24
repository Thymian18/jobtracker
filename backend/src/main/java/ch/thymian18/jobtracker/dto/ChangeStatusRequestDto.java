package ch.thymian18.jobtracker.dto;

import ch.thymian18.jobtracker.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequestDto(@NotNull ApplicationStatus status) {
}
