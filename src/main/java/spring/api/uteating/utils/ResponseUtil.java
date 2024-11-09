package spring.api.uteating.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.api.uteating.dto.ResponseDTO;


public class ResponseUtil {
    public static <T> ResponseEntity<ResponseDTO<T>> successResponse(T data, String message) {
        return new ResponseEntity<>(new ResponseDTO<>(
                HttpStatus.OK.value(),
                true,
                message,
                data
        ), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> errorResponse(T data, String message, HttpStatus status) {
        return new ResponseEntity<>(new ResponseDTO<>(
                status.value(),
                false,
                message,
                data
        ), status);
    }
}

