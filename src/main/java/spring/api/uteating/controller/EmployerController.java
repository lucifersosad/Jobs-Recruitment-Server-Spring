package spring.api.uteating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.api.uteating.dto.ResponseDTO;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.response.ResponseModel;
import spring.api.uteating.service.EmployerService;
import spring.api.uteating.utils.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("${api.BASE_URL}/employers")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<EmployerModel>>> getAllEmployers() {
        List<EmployerModel> data = employerService.getAllEmployers();
        return ResponseUtil.successResponse(data, "Truy vấn thành công");
    }
}
