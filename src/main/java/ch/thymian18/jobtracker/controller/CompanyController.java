package ch.thymian18.jobtracker.controller;

import ch.thymian18.jobtracker.dto.CompanyRequestDto;
import ch.thymian18.jobtracker.dto.CompanyResponseDto;
import ch.thymian18.jobtracker.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyResponseDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponseDto getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody @Valid CompanyRequestDto requestDto) {
        CompanyResponseDto created = companyService.createCompany(requestDto);
        URI location = URI.create("/companies/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public CompanyResponseDto updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyRequestDto requestDto) {
        return companyService.updateCompany(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
