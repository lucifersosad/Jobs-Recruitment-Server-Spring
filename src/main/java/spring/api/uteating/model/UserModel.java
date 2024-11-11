package spring.api.uteating.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.api.uteating.entity.Role;
import spring.api.uteating.entity.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String full_name;
    private String email;
    private String avatar;
    private String phone;
    private String address;
    private String gender;
    private boolean status;
}