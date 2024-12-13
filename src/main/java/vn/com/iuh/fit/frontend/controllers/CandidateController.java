package vn.com.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.iuh.fit.backend.models.Address;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.repositories.CandidateRepository;
import vn.com.iuh.fit.backend.services.AddressService;
import vn.com.iuh.fit.backend.services.CandidateService;
import vn.com.iuh.fit.backend.services.JobService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private JobService jobService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/index")
    public String homePage(HttpSession session, Model model) {
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        boolean isLoggedIn = candidate != null;
        model.addAttribute("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            List<Job> suggestedJobs = jobService.getSuggestedJobsForCandidate(candidate);
            model.addAttribute("suggestedJobs", suggestedJobs);
            model.addAttribute("candidate", candidate);
        } else {
            List<Job> allJobs = jobService.getAllJobsWithSkills();
            model.addAttribute("allJobs", allJobs);
        }
        return "Candidate/index";
    }

    @GetMapping("/search-results")
    public String showSearchJobPage(@RequestParam String jobName, Model model) {
        List<Job> jobs = jobService.findAllJobsWithSkillsByName(jobName);
        if (jobs.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy công việc phù hợp");
        } else {
            model.addAttribute("jobName", jobName);
            model.addAttribute("searchJobs", jobs);
        }
        return "Job/job-list";
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        return "Candidate/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, Model model) {
        Candidate candidate = candidateService.login(email);
        if (candidate != null) {
            session.setAttribute("candidate", candidate);
            return "redirect:/user/index?name=" + candidate.getFullName();
        } else {
            model.addAttribute("error", "Email không tồn tại hoặc không hợp lệ");
            return "/Candidate/login";
        }
    }

    @RequestMapping("/candidate-info")
    public String showProfilePage(HttpSession session, Model model) {
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        Address address = addressService.getAddressById(candidate.getAddress().getId());
        candidate.setAddress(address);
        if (candidate != null) {
            model.addAttribute("candidate", candidate);
            return "Candidate/candidate-info";
        } else {
            return "redirect:/user/index?name=" + candidate.getFullName();
        }
    }

    @PostMapping("/update-candidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        Candidate existingCandidate = candidateService.findById(candidate.getId());
        // Cập nhật các trường thông tin ứng viên
        existingCandidate.setFullName(candidate.getFullName());
        existingCandidate.setEmail(candidate.getEmail());
        existingCandidate.setPhone(candidate.getPhone());
        existingCandidate.setDob(candidate.getDob());
        // Kiểm tra và cập nhật Address
        if (existingCandidate.getAddress() != null) {
            // Cập nhật các thuộc tính của Address nếu Address đã tồn tại
            existingCandidate.getAddress().setNumber(candidate.getAddress().getNumber());
            existingCandidate.getAddress().setStreet(candidate.getAddress().getStreet());
            existingCandidate.getAddress().setCity(candidate.getAddress().getCity());
            existingCandidate.getAddress().setCountry(candidate.getAddress().getCountry());
            // Lưu ứng viên đã cập nhật
            System.out.println(existingCandidate);
            candidateService.save(existingCandidate);
        }
        return "redirect:/user/candidate-info";
    }

    @RequestMapping("/signup")
    public String showRegisterPage() {
        return "Candidate/signup";
    }

    @PostMapping("/signup")
    public String register(Candidate candidate, Address address, Model model) {
        Address savedAddress = addressService.saveAddress(address);
        candidate.setAddress(savedAddress);
        Candidate savedCandidate = candidateService.save(candidate);
        savedCandidate.setAddress(savedAddress);
        if (savedCandidate != null) {
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra, vui lòng thử lại sau");
            return "Candidate/signup";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("candidate");
        session.invalidate();
        return "redirect:/user/index";
    }
}
