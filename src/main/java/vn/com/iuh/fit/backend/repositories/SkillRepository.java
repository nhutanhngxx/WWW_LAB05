package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.models.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
