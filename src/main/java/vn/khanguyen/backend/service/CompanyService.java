package vn.khanguyen.backend.service;

import org.springframework.stereotype.Service;

import vn.khanguyen.backend.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

}
