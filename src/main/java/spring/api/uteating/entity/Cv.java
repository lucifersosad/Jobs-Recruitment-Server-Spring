package spring.api.uteating.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cv")

public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "file_name")
    private String FileName;



    @Column(name = "file_type")
    private String Type;


    @Column(name = "cv_id")
    private String Cv_id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time", updatable = false)
    private Date uploadTime;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user ;
}
