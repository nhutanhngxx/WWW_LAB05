package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.Company;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.repositories.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {
    @Autowired CompanyRepository companyRepository;
    public Company login(String email) {
        return companyRepository.findByEmail(email);
    }
    public Company save(Company company) {
        return companyRepository.save(company);
    }
}
