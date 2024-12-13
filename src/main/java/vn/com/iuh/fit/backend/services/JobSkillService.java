package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.JobSkill;
import vn.com.iuh.fit.backend.repositories.JobSkillRepository;

@Service
public class JobSkillService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    public JobSkill saveJobSkill(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }
}
