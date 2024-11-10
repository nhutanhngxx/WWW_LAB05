package vn.com.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.repositories.CandidateRepository;
import vn.com.iuh.fit.backend.services.CandidateService;
import vn.com.iuh.fit.backend.services.JobService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired    private CandidateRepository candidateRepository;
    @Autowired    private CandidateService candidateService;
    @Autowired    private JobService jobService;

    @GetMapping("/index")
    public String homePage(HttpSession session, Model model) {
        Candidate candidate = (Candidate) session.getAttribute("candidate");
        boolean isLoggedIn = candidate != null;
        model.addAttribute("isLoggedIn", isLoggedIn);
        if (candidate != null) {
            List<Job> suggestedJobs = jobService.getSuggestedJobsForCandidate(candidate);
            model.addAttribute("suggestedJobs", suggestedJobs);
            model.addAttribute("candidate", candidate);
        }
        return "Home/index";
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        return "Home/login";
    }

    @RequestMapping("/signup")
    public String showRegisterPage() {
        return "Home/signup";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, Model model) {
        Candidate candidate = candidateService.login(email);
        System.out.println(candidate);
        System.out.println(email);
        if (candidate != null) {
            session.setAttribute("candidate", candidate);
            return "redirect:/user/index";
        } else {
            model.addAttribute("error", "Email không tồn tại hoặc không hợp lệ");
            return "/Home/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("candidate");
        session.invalidate();
        return "redirect:/user/index";
    }
}
