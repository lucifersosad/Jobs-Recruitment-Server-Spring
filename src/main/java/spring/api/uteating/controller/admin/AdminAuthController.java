package spring.api.uteating.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.model.SignInDTO;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.EmployerRepository;
import spring.api.uteating.repository.RoleRepository;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.JwtService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.ADMIN}/auth")
public class AdminAuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployerRepository employerRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody SignInDTO authRequest){

        Optional<Employer> employerOptional = employerRepository.findByEmail(authRequest.getEmail());

        if (employerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Email không tồn tại"));
        }

        Map<String, Object> tokenDetails = jwtService.generateToken(authRequest.getEmail(), 1);
        UserModel userModel = new UserModel();
        EmployerModel employerModel = new EmployerModel();

        User user1 = employerRepository.getEmployerByEmail(authRequest.getEmail());
//        User user = userRepository.getUserByUsernameOrEmail(authRequest.getEmail());
        BeanUtils.copyProperties(user1, employerModel);

        Map<String, Object> response = new HashMap<>(tokenDetails);
        response.put("info", user1);

        return ResponseEntity.ok(response);
    }
}
