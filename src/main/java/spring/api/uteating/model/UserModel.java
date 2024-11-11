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
    @JsonProperty("full_name")
    private String fullName;
    private String email;
    private String avatar;
    private String phone;
    private String address;

    public UserModel(User user) {
        this.id = user.getUserId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.phone = user.getPhone();
        this.address = user.getAddress();
    }
}