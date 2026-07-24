package ch.thymian18.jobtracker.service;

import ch.thymian18.jobtracker.dto.ApplicationRequestDto;
import ch.thymian18.jobtracker.dto.ApplicationResponseDto;
import ch.thymian18.jobtracker.dto.StatusHistoryResponseDto;
import ch.thymian18.jobtracker.entity.Application;
import ch.thymian18.jobtracker.entity.ApplicationStatus;
import ch.thymian18.jobtracker.entity.Company;
import ch.thymian18.jobtracker.entity.StatusHistory;
import ch.thymian18.jobtracker.repository.ApplicationRepository;
import ch.thymian18.jobtracker.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public ApplicationService(ApplicationRepository applicationRepository, CompanyRepository companyRepository) {
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    //@GetMapping
    public List<ApplicationResponseDto> getAllApplications() {
        return applicationRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    //@GetMapping("/{id}")
    public ApplicationResponseDto getApplicationById(/*@PathVariable */Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));
        return toResponseDto(application);
    }

    //@PostMapping
    public ApplicationResponseDto createApplication(ApplicationRequestDto requestDto) {
        Company company = companyRepository.findById(requestDto.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + requestDto.companyId()));

        Application application = new Application();
        application.setPosition(requestDto.position());
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedDate(requestDto.appliedDate());
        application.setJobPostingText(requestDto.jobPostingText());
        application.setCompany(company);

        StatusHistory history = new StatusHistory();
        history.setStatus(ApplicationStatus.APPLIED);
        history.setChangedAt(LocalDateTime.now());
        history.setApplication(application);

        application.getStatusHistory().add(history);

        Application saved = applicationRepository.save(application);
        return toResponseDto(saved);
    }

    public ApplicationResponseDto updateApplication(Long id, ApplicationRequestDto requestDto) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));
        Company company = companyRepository.findById(requestDto.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + requestDto.companyId()));

        application.setPosition(requestDto.position());
        application.setAppliedDate(requestDto.appliedDate());
        application.setJobPostingText(requestDto.jobPostingText());
        application.setCompany(company);



        Application updated = applicationRepository.save(application);
        return toResponseDto(updated);
    }

    public ApplicationResponseDto changeApplicationStatus(Long id, ApplicationStatus newStatus) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

        application.setStatus(newStatus);

        StatusHistory history = new StatusHistory();
        history.setStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());
        history.setApplication(application);
        application.getStatusHistory().add(history);

        Application updated = applicationRepository.save(application);
        return toResponseDto(updated);
    }

    public void deleteApplication(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

        applicationRepository.delete(application);
    }

    private ApplicationResponseDto toResponseDto(Application application) {
        List<StatusHistoryResponseDto> statusHistoryResponseDtos = application.getStatusHistory()
                .stream()
                .map(h -> new StatusHistoryResponseDto(h.getId(), h.getStatus(), h.getChangedAt()))
                .toList();

        return new ApplicationResponseDto(
                application.getId(),
                application.getPosition(),
                application.getStatus(),
                application.getAppliedDate(),
                application.getJobPostingText(),
                application.getCompany().getId(),
                application.getCompany().getName(),
                statusHistoryResponseDtos
        );
    }
}