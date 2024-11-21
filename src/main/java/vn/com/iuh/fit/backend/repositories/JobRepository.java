package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.models.Company;
import vn.com.iuh.fit.backend.models.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findJobByJobName(String jobName);

    List<Job> findByIdIn(List<Long> jobIds);

    //    @Query("SELECT j FROM Job j " +
//            "JOIN JobSkill js ON js.job.id = j.id " +
//            "JOIN Skill s ON js.skill.id = s.id " +
//            "WHERE j.company.id = :companyId")
//    List<Job> findJobsByCompany(@Param("companyId") Long companyId);
    @Query("SELECT j FROM Job j " +
            "JOIN FETCH JobSkill js ON js.job.id = j.id " +
            "JOIN FETCH Skill s ON js.skill.id = s.id " +
            "WHERE j.company.id = :companyId")
    List<Job> findJobsByCompanyWithSkills(@Param("companyId") Long companyId);

    // Liệt kê danh sách công việc cùng với danh sách kỹ năng của công việc đó
    // Chỉ những công việc có ít nhất 1 kỹ năng
    @Query("SELECT j FROM Job j " +
            "JOIN FETCH j.jobSkills js " +
            "JOIN FETCH js.skill s")
    List<Job> findAllJobsWithSkills();

//    Liệt kê danh sách công việc cùng với danh sách kỹ năng của công việc đó theo tên công việc
    @Query("SELECT j FROM Job j " +
            "JOIN FETCH j.jobSkills js " +
            "JOIN FETCH js.skill s " +
            "WHERE lower(j.jobName) LIKE lower(CONCAT('%', :jobName, '%'))")
    List<Job> findAllJobsWithSkillsByName(@Param("jobName") String jobName);
}