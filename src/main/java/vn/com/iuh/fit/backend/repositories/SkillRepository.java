package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.models.Job;
import vn.com.iuh.fit.backend.models.Skill;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // Danh sách kỹ năng theo id công việc
    @Query("SELECT s FROM Skill s WHERE s.id IN (SELECT js.skill.id FROM JobSkill js WHERE js.job.id = :jobId)")
    List<Skill> findSkillsByJobId(@Param("jobId") Long jobId);
}