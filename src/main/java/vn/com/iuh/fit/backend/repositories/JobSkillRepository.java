package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.ids.JobSkillId;
import vn.com.iuh.fit.backend.models.JobSkill;

import java.util.List;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {

    // Tìm kiếm danh sách id công việc theo danh sách id kỹ năng
    @Query("SELECT js.job.id FROM JobSkill js WHERE js.skill.id IN :skillIds")
    List<Long> findJobIdsBySkillIds(@Param("skillIds") List<Long> skillIds);

    // Tìm kiếm danh sách id kỹ năng theo id công việc
    @Query("SELECT js.skill.id FROM JobSkill js WHERE js.job.id = :jobId")
    List<Long> findSkillIdsByJobId(@Param("jobId") Long jobId);
}