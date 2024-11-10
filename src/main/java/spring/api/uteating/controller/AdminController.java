package spring.api.uteating.controller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.ErrorReponse;
import spring.api.uteating.model.SignInDTO;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.RoleRepository;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.JwtService;
import spring.api.uteating.service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.BASE_URL}/admin")
public class AdminController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("Welcome admin");
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Unauthorized");
            response.put("statusCode", 401);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
