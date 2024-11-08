package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.models.Skill;
import vn.com.iuh.fit.backend.services.JobService;
import vn.com.iuh.fit.backend.services.SkillService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jobs")
public class JobContronller {
    @Autowired private JobService jobService;
    @Autowired private SkillService skillService;

    @GetMapping("/new")
    public String showJobForm(Model model) {
        model.addAttribute("job", new Job());
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "jobs/add-job";
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute Job job, @RequestParam List<Long> skillIds) {
        jobService.addJobWithSkills(job, skillIds);
        return "redirect:/jobs/job-list";
    }

    @GetMapping("/list")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobs/job-list";
    }
}
