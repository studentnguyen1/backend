package vn.khanguyen.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.khanguyen.backend.domain.Company;
import vn.khanguyen.backend.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<Company> {
    User findByEmail(String email);

}
