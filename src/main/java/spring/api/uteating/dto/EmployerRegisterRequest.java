package spring.api.uteating.dto;

import lombok.Data;

@Data
public class EmployerRegisterRequest {
    private String email;
    private String password;
    private String companyName;
    private String position;
}
