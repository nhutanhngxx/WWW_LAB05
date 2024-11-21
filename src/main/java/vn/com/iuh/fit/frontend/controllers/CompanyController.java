package vn.com.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.iuh.fit.backend.models.Address;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.models.Company;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.services.AddressService;
import vn.com.iuh.fit.backend.services.CandidateService;
import vn.com.iuh.fit.backend.services.CompanyService;
import vn.com.iuh.fit.backend.services.JobService;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CandidateService candidateService;

    public CompanyController(CompanyService companyService, JobService jobService, AddressService addressService, CandidateService candidateService) {
        this.companyService = companyService;
        this.jobService = jobService;
        this.addressService = addressService;
        this.candidateService = candidateService;
    }

    @GetMapping("/index")
    public String showHomePage(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("company");
        boolean isLoggedIn = company != null;
        List<Candidate> candidates = candidateService.getAll();
        model.addAttribute("candidates", candidates);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "Company/index";
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        return "Company/login";
    }

    @RequestMapping("/signup")
    public String showRegisterPage() {
        return "Company/signup";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, Model model) {
        Company company = companyService.login(email);
        if (company != null) {
            session.setAttribute("company", company);
            return "redirect:/company/index?name=" + company.getCompName();
        } else {
            model.addAttribute("error", "Email không tồn tại hoặc không hợp lệ");
            return "Company/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("company");
        return "redirect:/company/login";
    }

    @PostMapping("/signup")
    public String register(Company company, Address address, Model model) {
        Address saveAddress = addressService.saveAddress(address);
        company.setAddress(saveAddress);
        Company savedCompany = companyService.save(company);
        savedCompany.setAddress(saveAddress);
        if (savedCompany != null) {
            return "redirect:/company/login";
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra");
            return "Company/signup";
        }
    }

    @RequestMapping("/job-postings")
    public String showJobPostings(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("company");
        Address address = addressService.getAddressById(company.getAddress().getId());
        company.setAddress(address);
        if (company != null) {
            List<Job> jobs = jobService.getJobsByCompany(company.getId());
            model.addAttribute("jobs", jobs);
        } else {
            return "redirect:/company/index";
        }
        return "Company/job-postings";
    }

    @RequestMapping("/company-info")
    public String showCompanyInfo(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("company");
        if (company != null) {
            model.addAttribute("company", company);
            return "Company/company-info";
        }
        return "redirect:/company/index";
    }

    @PostMapping("/update")
    public String updateCompanyInfo(Company company, HttpSession session) {
        Company currentCompany = (Company) session.getAttribute("company");
        if (currentCompany != null) {
            currentCompany.setCompName(company.getCompName());
            currentCompany.setAbout(company.getAbout());
            currentCompany.setPhone(company.getPhone());
            currentCompany.setEmail(company.getEmail());
            currentCompany.setWebUrl(company.getWebUrl());
            currentCompany.getAddress().setNumber(company.getAddress().getNumber());
            currentCompany.getAddress().setStreet(company.getAddress().getStreet());
            currentCompany.getAddress().setCity(company.getAddress().getCity());
            currentCompany.getAddress().setCountry(company.getAddress().getCountry());

            companyService.save(currentCompany);
            session.setAttribute("company", currentCompany);
        }
        return "redirect:/company/index";

    }

    @GetMapping("/job-postings/create-new_post")
    public String showCreateJobForm(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("company");
        if (company != null) {
            model.addAttribute("company", company);
            return "Company/create-new-post";
        }
        return "redirect:/company/index";
    }

    @GetMapping("/search-results")
    public String showSearchJobPage(@RequestParam String jobName, Model model) {
        List<Job> jobs = jobService.findAllJobsWithSkillsByName(jobName);
        if (jobs.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy công việc phù hợp");
        } else {
            model.addAttribute("jobName", jobName);
            model.addAttribute("jobs", jobs);
        }
        return "Company/job-postings";
    }
}
