package spring.api.uteating.service;

import org.springframework.beans.factory.annotation.Autowired;
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

//    public List<EmployerModel> getAllEmployers() {
//        return employerRepository.findAll().stream()
//                .map(EmployerModel::new)
//                .collect(Collectors.toList());
//    }

    public List<Employer> getEmployers() {
        return employerRepository.findAll();
    }
}
