package spring.api.uteating.response;

import lombok.Data;

@Data
public class ResponseModel<T> {
    private int statusCode;
    private boolean status;
    private String message;
    private T data;

    public ResponseModel() {}

    public ResponseModel(int statusCode, boolean status, String message, T data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
