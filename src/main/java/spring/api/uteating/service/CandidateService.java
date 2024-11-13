package spring.api.uteating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.api.uteating.entity.Candidate;
import spring.api.uteating.repository.CandidateRepository;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    public Candidate validateAndGetOne(Long id) {
        return candidateRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy uứng viên"));
    }

    public Candidate getOneByEmail(String email) {
        return candidateRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy uứng viên"));
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public void delete(Candidate candidate) {
        candidateRepository.delete(candidate);
    }
}
