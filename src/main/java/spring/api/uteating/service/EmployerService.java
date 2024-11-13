package spring.api.uteating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.repository.EmployerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> getEmployers() {
        return employerRepository.findAll();
    }

    public Employer validateAndGetEmployer(Long id) {
        return employerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy nhà tuyển dụng"));
    }

    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    public void deleteEmployer(Employer employer) {
        employerRepository.delete(employer);
    }
}
