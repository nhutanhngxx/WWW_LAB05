package vn.com.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.iuh.fit.backend.models.Candidate;

import java.util.List;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidate, Long> {
    Page<Candidate> findAll(Pageable pageable);
    List<Candidate> findByFullNameContainingIgnoreCase(String name);
}
