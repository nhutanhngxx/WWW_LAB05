package vn.com.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.iuh.fit.backend.models.Candidate;
import vn.com.iuh.fit.backend.repositories.CandidatesRepository;

@Service
public class CandidatesService {
    @Autowired
    private CandidatesRepository candidatesRepository;

    public boolean addCandidate(Candidate candidate) {
        candidatesRepository.save(candidate);
        return true;
    }

    public void save(Candidate candidate) {
        candidatesRepository.save(candidate);
    }

    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidatesRepository.findAll(pageable);
    }
}
