package spring.api.uteating.controller.employer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.api.uteating.dto.EmployerRegisterDTO;
import spring.api.uteating.dto.LoginDTO;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.repository.EmployerRepository;
import spring.api.uteating.service.JwtService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.EMPLOYER}/auth")
public class AuthEmployerController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody EmployerRegisterDTO registerDTO) {
        Employer employer = new Employer();
        BeanUtils.copyProperties(registerDTO, employer);
        employer.setStatus(true);
        employer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        employerRepository.save(employer);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployer(@RequestBody LoginDTO loginDTO) {

        Optional<Employer> employerOptional = employerRepository.findByEmail(loginDTO.getEmail());

        if (employerOptional.isEmpty()) {
            throw new UsernameNotFoundException("Tài khoản chưa đăng ký!");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Mật khẩu không đúng");
        }

        Map<String, Object> tokenDetails = jwtService.generateToken(loginDTO.getEmail(), 3);
        Map<String, Object> response = new HashMap<>(tokenDetails);

        Employer employer = employerOptional.get();
        EmployerModel employerModel = new EmployerModel();
        BeanUtils.copyProperties(employer, employerModel);
        employerModel.setId(employer.getUserId());
        employerModel.setCompany_name(employer.getCompanyName());

        response.put("info", employerModel);

        return ResponseEntity.ok(response);
    }
}
