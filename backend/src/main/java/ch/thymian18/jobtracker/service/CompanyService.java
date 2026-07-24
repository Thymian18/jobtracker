package ch.thymian18.jobtracker.service;

import ch.thymian18.jobtracker.dto.CompanyRequestDto;
import ch.thymian18.jobtracker.dto.CompanyResponseDto;
import ch.thymian18.jobtracker.entity.Company;
import ch.thymian18.jobtracker.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// der Service ist der "Übersetzer" zwischen entity "Company" und DTO "CompanyRequestDto/..."
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    public CompanyResponseDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        return toResponseDto(company);
    }

    public CompanyResponseDto createCompany(CompanyRequestDto dto) {
        Company company = new Company();
        company.setName(dto.name());
        company.setWebsite(dto.website());

        Company saved = companyRepository.save(company);
        return toResponseDto(saved);
    }

    public CompanyResponseDto updateCompany(Long id, CompanyRequestDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        company.setName(dto.name());
        company.setWebsite(dto.website());

        Company updated = companyRepository.save(company);
        return toResponseDto(updated);
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        companyRepository.delete(company);
    }

    private CompanyResponseDto toResponseDto(Company company) {
        return new CompanyResponseDto(company.getId(), company.getName(), company.getWebsite());
    }
}
