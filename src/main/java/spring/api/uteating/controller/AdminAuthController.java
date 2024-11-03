package spring.api.uteating.controller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.SignInDTO;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.RoleRepository;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody SignInDTO authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsernameOrEmail(), authRequest.getPassword())
        );
        Map<String, Object> tokenDetails = jwtService.generateToken(authRequest.getUsernameOrEmail(), 1);
        UserModel userModel = new UserModel();
        User user = userRepository.getUserByUsernameOrEmail(authRequest.getUsernameOrEmail());
        BeanUtils.copyProperties(user, userModel);

        Map<String, Object> response = new HashMap<>(tokenDetails);
        response.put("info", userModel);

        return ResponseEntity.ok(response);
    }
}
