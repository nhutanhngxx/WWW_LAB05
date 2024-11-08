package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.iuh.fit.backend.repositories.CompanyRepository;
import vn.com.iuh.fit.backend.services.CompanyService;

@Controller
public class CompanyController {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private CompanyService companyService;
    @GetMapping("/companies")
    public String showCompanies(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "company/company-list";
    }
}
