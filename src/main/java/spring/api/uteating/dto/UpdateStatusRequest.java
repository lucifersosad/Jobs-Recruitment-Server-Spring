package spring.api.uteating.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;
}
