package spring.api.uteating.config;


import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApiConfig {
    @Value("${api.BASE_URL}")
    private String baseUrl;
    @Value("${prefix.ADMIN}")
    private String prefixAdmin;
    @Value("${prefix.EMPLOYER}")
    private String prefixEmployer;
}

