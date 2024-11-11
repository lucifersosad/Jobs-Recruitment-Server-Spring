package spring.api.uteating.dto;

import lombok.Data;

@Data
public class EmployerRegisterDTO {
    private String email;
    private String password;
    private String companyName;
    private String position;
}
