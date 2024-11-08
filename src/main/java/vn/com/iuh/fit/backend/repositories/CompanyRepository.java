package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
