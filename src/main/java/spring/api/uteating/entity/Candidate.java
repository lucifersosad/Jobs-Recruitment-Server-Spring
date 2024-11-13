package spring.api.uteating.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "candidates")
public class Candidate extends User implements Serializable {

    @Column(name="active_job_search", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean activeJobSearch;

    @Column(name="allow_search",columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean allowSearch;

    @Column(name="year_of_experience")
    private Long yearOfExperience;

    @Column(name="desired_salary")
    private Long desiredSalary;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Cv> cvs;
}