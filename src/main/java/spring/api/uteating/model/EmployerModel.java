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
public class EmployerModel extends UserModel {
    private String company_name;
    private String position;
}