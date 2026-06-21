package ch.thymian18.jobtracker.dto;

public record ContactResponseDto(Long id, String name, String role, String email, Long companyId, String companyName) {
}
