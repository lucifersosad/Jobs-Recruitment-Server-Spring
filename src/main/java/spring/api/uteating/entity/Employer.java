package spring.api.uteating.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employers")
public class Employer implements Serializable {
    @Id
    private Long id;

    private String companyName;

    private String position;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private User user;
}
