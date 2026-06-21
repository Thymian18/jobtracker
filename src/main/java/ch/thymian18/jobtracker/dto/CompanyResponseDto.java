package ch.thymian18.jobtracker.dto;

// DTO sind dafür, dass die API gegen aussen nur das zeigt, was man möchte und nicht automatisch alles, was in der DB ist
public record CompanyResponseDto(Long id, String name, String website) {
}
