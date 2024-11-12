package spring.api.uteating.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    private String gender;

    private String phone;

    private String address;

    @Column(name = "avatar")
    private String avatar;

    private String password;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean status;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)



    @JsonManagedReference
    private List<Cv> cvs;
}
