package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.models.Skill;
import vn.com.iuh.fit.backend.repositories.JobRepository;
import vn.com.iuh.fit.backend.repositories.SkillRepository;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;

    public void addJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public void addJobWithSkills(Job job, List<Long> skillIds) {
    }
}
