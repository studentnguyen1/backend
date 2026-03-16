package vn.khanguyen.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.khanguyen.backend.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
