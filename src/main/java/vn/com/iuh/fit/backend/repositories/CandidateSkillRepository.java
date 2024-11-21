package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.ids.CandidateSkillId;
import vn.com.iuh.fit.backend.models.CandidateSkill;

import java.util.List;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    // Tìm kiếm danh sách id kỹ năng theo id ứng viên
    @Query("SELECT cs.skill.id FROM CandidateSkill cs WHERE cs.can.id = :candidateId")
    List<Long> findSkillIdsByCandidateId(@Param("candidateId") Long candidateId);
}