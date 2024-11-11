package spring.api.uteating.controller.admin;

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
import spring.api.uteating.dto.LoginDTO;
import spring.api.uteating.entity.Admin;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.model.SignInDTO;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.AdminRepository;
import spring.api.uteating.repository.EmployerRepository;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.JwtService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.ADMIN}/auth")
public class AuthAdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO){

        Optional<Admin> opt = adminRepository.findByEmail(loginDTO.getEmail());

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("Tài khoản chưa đăng ký!");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Mật khẩu không đúng");
        }

        Map<String, Object> tokenDetails = jwtService.generateToken(loginDTO.getEmail(), 1);
        Map<String, Object> response = new HashMap<>(tokenDetails);

        Admin admin = opt.get();
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(admin, userModel);
        userModel.setId(admin.getUserId());

        response.put("info", userModel);

        return ResponseEntity.ok(response);
    }
}
