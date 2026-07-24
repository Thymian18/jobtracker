package ch.thymian18.jobtracker.controller;

import ch.thymian18.jobtracker.dto.ApplicationRequestDto;
import ch.thymian18.jobtracker.dto.ApplicationResponseDto;
import ch.thymian18.jobtracker.dto.ChangeStatusRequestDto;
import ch.thymian18.jobtracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationResponseDto> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ApplicationResponseDto getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id);
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDto> createApplication(@RequestBody @Valid ApplicationRequestDto requestDto) {
        ApplicationResponseDto created =  applicationService.createApplication(requestDto);
        URI location = URI.create("/applications/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ApplicationResponseDto updateApplication(@PathVariable Long id, @RequestBody @Valid ApplicationRequestDto requestDto) {
        return applicationService.updateApplication(id, requestDto);
    }

    // Patch anstatt Put, weil nur ein Teil der ressource geändert wird (ein Teil der Application)
    @PatchMapping("/{id}/status")
    public ApplicationResponseDto changeApplicationStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusRequestDto dto) {
        return applicationService.changeApplicationStatus(id, dto.status());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
