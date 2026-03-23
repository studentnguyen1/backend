package vn.khanguyen.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.khanguyen.backend.domain.Company;
import vn.khanguyen.backend.domain.User;
import vn.khanguyen.backend.domain.dto.Meta;
import vn.khanguyen.backend.domain.dto.ResultPaginationDTO;
import vn.khanguyen.backend.repository.CompanyRepository;
import vn.khanguyen.backend.repository.UserRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company createCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public Company findById(long id) {
        return this.companyRepository.findById(id).orElse(null);
    }

    public Company updateCompany(Company company) {
        Company currentCompany = this.findById(company.getId());
        currentCompany.setLogo(company.getLogo());
        currentCompany.setName(company.getName());
        currentCompany.setDescription(company.getDescription());
        currentCompany.setAddress(company.getAddress());
        return this.companyRepository.save(currentCompany);
    }

    public ResultPaginationDTO getAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());

        return rs;
    }

    public void deleteCompany(long id) {
        List<User> users = this.userRepository.findByCompany(findById(id));
        this.userRepository.deleteAll(users);

        this.companyRepository.deleteById(id);
    }

}
