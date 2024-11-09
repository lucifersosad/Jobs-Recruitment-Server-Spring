package spring.api.uteating.model;

import lombok.Data;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.User;

@Data
public class EmployerModel {
    private Long id;
    private String company_name;
    private String company_address;
    private String position;
    private String username;
    private String fullName;
    private String email;

    public EmployerModel(Employer employer) {
        this.id = employer.getId();
        this.company_name = employer.getCompanyName();
        this.position = employer.getPosition();

        User user = employer.getUser();
        if (user != null) {
            this.username = user.getUsername();
            this.fullName = user.getFullName();
            this.email = user.getEmail();
        }
    }
}