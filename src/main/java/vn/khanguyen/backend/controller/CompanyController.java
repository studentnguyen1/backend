package vn.khanguyen.backend.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.khanguyen.backend.domain.Company;
import vn.khanguyen.backend.domain.dto.ResultPaginationDTO;
import vn.khanguyen.backend.service.CompanyService;
import vn.khanguyen.backend.util.annotation.ApiMessage;
import vn.khanguyen.backend.util.error.ResourceNotFoundException;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    @ApiMessage("Create a company")
    public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company) throws ResourceNotFoundException {
        if (this.companyService.findById(company.getId()) != null) {
            throw new ResourceNotFoundException("Company da ton tai");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.createCompany(company));
    }

    @PutMapping("/companies")
    @ApiMessage("Update a company")
    public ResponseEntity<Company> updateCompany(@RequestBody @Valid Company company) throws ResourceNotFoundException {
        if (this.companyService.findById(company.getId()) == null) {
            throw new ResourceNotFoundException("Company khong ton tai");
        }
        Company companyCurrent = this.companyService.updateCompany(company);
        return ResponseEntity.status(HttpStatus.OK).body(companyCurrent);
    }

    @DeleteMapping("/companies/{id}")
    @ApiMessage("Delete a company")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) throws ResourceNotFoundException {
        if (this.companyService.findById(id) == null) {
            throw new ResourceNotFoundException("Company khong ton tai");
        }
        this.companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/companies")
    @ApiMessage("Fetch all company")
    public ResponseEntity<ResultPaginationDTO> getAllCompany(@Filter Specification<Company> spec, Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.getAllCompany(spec, pageable));
    }

    @GetMapping("/companies/{id}")
    @ApiMessage("Fetch company by id")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") long id) throws ResourceNotFoundException {
        Company company = this.companyService.findById(id);
        if (company == null) {
            throw new ResourceNotFoundException("Company khong ton tai");
        }
        return ResponseEntity.status(HttpStatus.OK).body(company);

    }
}