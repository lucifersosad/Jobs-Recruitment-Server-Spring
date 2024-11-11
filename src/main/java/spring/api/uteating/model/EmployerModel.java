package spring.api.uteating.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerModel {

    private Long id;
    private String full_name;
    private String email;
    private String avatar;
    private String phone;
    private String address;
    private String company_name;
    private String position;

    public EmployerModel(Employer employer) {
        id = employer.getUserId();
        email = employer.getEmail();
        avatar = employer.getAvatar();
        phone = employer.getPhone();
        address = employer.getAddress();
        company_name = employer.getCompanyName();
        position = employer.getPosition();
    }
}