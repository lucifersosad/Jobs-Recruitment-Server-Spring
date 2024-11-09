package spring.api.uteating.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private int statusCode;
    private boolean status;
    private String message;
    private T data;

    public ResponseDTO() {}

    public ResponseDTO(int statusCode, boolean status, String message, T data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
