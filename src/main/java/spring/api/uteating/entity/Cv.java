package spring.api.uteating.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cvs")

public class Cv {
    @Id
    @Column(name = "cv_id")
    private String cvId;

    private String name;

    private String type;

    @Column(name = "origin_url")
    private String originUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private Candidate candidate;
}
