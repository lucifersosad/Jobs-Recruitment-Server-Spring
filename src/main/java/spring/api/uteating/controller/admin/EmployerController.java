package spring.api.uteating.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.api.uteating.dto.ResponseDTO;
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
//    public ResponseEntity<ResponseDTO<List<EmployerModel>>> getAllEmployers() {
//        List<EmployerModel> data = employerService.getAllEmployers();
//        return ResponseUtil.successResponse(data, "Truy vấn thành công");
//    }
}