package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.*;
import vn.com.iuh.fit.backend.repositories.*;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<Job> getSuggestedJobsForCandidate(Candidate candidate) {
        List<Long> skillIds = candidateSkillRepository.findSkillIdsByCandidateId(candidate.getId());
        List<Long> skills = jobSkillRepository.findJobIdsBySkillIds(skillIds);
        return jobRepository.findByIdIn(skills);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Lấy danh sách công việc cùng với danh sách kỹ năng của công việc đó
    public List<Job> getAllJobsWithSkills() {
        return jobRepository.findAllJobsWithSkills();
    }

    public List<Job> getJobsByCompany(Long companyId) {
        return jobRepository.findJobsByCompanyWithSkills(companyId);
    }

    // Lấy danh sách công việc theo tên
    public List<Job> getJobsByName(String name) {
        return jobRepository.findJobByJobName(name);
    }

    public List<Job> findAllJobsWithSkillsByName(String jobName) {
        return jobRepository.findAllJobsWithSkillsByName(jobName);
    }

}
