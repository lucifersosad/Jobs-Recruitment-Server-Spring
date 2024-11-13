package spring.api.uteating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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