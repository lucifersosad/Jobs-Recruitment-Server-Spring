package spring.api.uteating.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadCvRequest {
    @NotNull(message = "CV không được để trống")
    private MultipartFile cv;
}
