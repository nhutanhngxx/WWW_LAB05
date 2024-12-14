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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.iuh.fit.backend.ids.JobSkillId;
import vn.com.iuh.fit.backend.models.*;
import vn.com.iuh.fit.backend.repositories.SkillRepository;
import vn.com.iuh.fit.backend.services.*;

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
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private SkillService skillService;
    @Autowired
    private JobSkillService jobSkillService;

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
        Address address = addressService.getAddressById(company.getAddress().getId());
        company.setAddress(address);
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

    @GetMapping("/job-postings/create-new-post")
    public String showCreateJobForm(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("company");
        List<Skill> skills = skillService.getAll();
        if (company != null) {
            model.addAttribute("company", company);
            model.addAttribute("skills", skills);
            return "Company/create-new-post";
        }
        return "redirect:/company/index";
    }

    @PostMapping("/job-postings/create-new-post")
    public String createJob(
            @RequestParam("jobName") String jobName,
            @RequestParam("jobDesc") String jobDesc,
            @RequestParam(value = "skills[]", required = false) List<Long> skillIds,
            @RequestParam(value = "newSkill", required = false) String newSkill,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Company company = (Company) session.getAttribute("company");
        if (company == null) {
            return "redirect:/company/index";
        } else {
            Job job = new Job();
            job.setJobName(jobName);
            job.setJobDesc(jobDesc);
            job.setCompany(company);
            jobService.saveJob(job);

            if (skillIds != null) {
                for (Long skillId : skillIds) {
                    Skill skill = skillRepository.findById(skillId).orElse(null);
                    JobSkill jobSkill = new JobSkill();
                    jobSkill.setId(new JobSkillId(job.getId(), skill.getId()));
                    jobSkill.setJob(job);
                    jobSkill.setSkill(skill);
                    jobSkillService.saveJobSkill(jobSkill);
                }
            }

            if (newSkill != null && !newSkill.trim().isEmpty()) {
                Skill skill = new Skill();
                skill.setSkillName(newSkill);
                skillService.save(skill);
                JobSkill jobSkill = new JobSkill();
                jobSkill.setId(new JobSkillId(job.getId(), skill.getId()));
                jobSkill.setSkillLevel(Byte.valueOf("1"));
                jobSkill.setJob(job);
                jobSkill.setSkill(skill);
                jobSkillService.saveJobSkill(jobSkill);
            }
            return "redirect:/company/job-postings";
        }
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

    @PostMapping("/job-postings/create-new_post")
    public String createJob(Job job, HttpSession session) {
        Company company = (Company) session.getAttribute("company");
        if (company != null) {
            job.setCompany(company);
            jobService.saveJob(job);
        }
        return "redirect:/company/job-postings";
    }
}
