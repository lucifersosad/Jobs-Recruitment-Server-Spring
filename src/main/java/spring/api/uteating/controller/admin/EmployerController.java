package spring.api.uteating.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.api.uteating.dto.ResponseDTO;
import spring.api.uteating.dto.UpdateStatusRequest;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.mapper.EmployerMapper;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.repository.EmployerRepository;
import spring.api.uteating.service.EmployerService;
import spring.api.uteating.utils.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.ADMIN}/employers")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerMapper employerMapper;

    @GetMapping
    public List<EmployerModel> getEmployers() {
        return employerService.getEmployers().stream().map(employerMapper::toEmployerModel).toList();
    }

    @GetMapping("/{id}")
    public EmployerModel getEmployer(@PathVariable Long id) {
        Employer employer = employerService.validateAndGetEmployer(id);
        return employerMapper.toEmployerModel(employer);
    }

    @PutMapping("/{id}/status")
    public EmployerModel updateStatusEmployer(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest updateStatusRequest) {
        Employer employer = employerService.validateAndGetEmployer(id);
        employerMapper.updateStatusFromDto(updateStatusRequest, employer);
        employer = employerService.saveEmployer(employer);
        return employerMapper.toEmployerModel(employer);
    }

    @DeleteMapping("/{id}")
    public EmployerModel deleteEmployer(@PathVariable Long id) {
        Employer employer = employerService.validateAndGetEmployer(id);
        employerService.deleteEmployer(employer);
        return employerMapper.toEmployerModel(employer);
    }
}