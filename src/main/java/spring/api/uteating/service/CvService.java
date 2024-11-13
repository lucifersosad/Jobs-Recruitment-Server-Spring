package spring.api.uteating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.api.uteating.entity.Cv;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.repository.CvRepository;

import java.util.List;

@Service
public class CvService {

    @Autowired
    private CvRepository cvRepository;

    public List<Cv> getCvs() {
        return cvRepository.findAll();
    }

    public Cv save(Cv cv) {
        return cvRepository.save(cv);
    }
}
