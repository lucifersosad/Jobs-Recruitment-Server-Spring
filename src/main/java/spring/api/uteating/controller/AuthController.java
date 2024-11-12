package spring.api.uteating.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.SignInDTO;
import spring.api.uteating.model.SignUpDTO;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.JwtService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome bitch";
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody SignInDTO authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        Map<String, Object> tokenDetails = jwtService.generateToken(authRequest.getEmail(), 2);
        UserModel userModel = new UserModel();
        User user = userRepository.getUserByEmail(authRequest.getEmail());
        BeanUtils.copyProperties(user, userModel);

        Map<String, Object> response = new HashMap<>(tokenDetails);
        response.put("info", userModel);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/generateToken")
    public Map<String, Object> authenticateAndGetToken(@RequestBody SignInDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
//        return jwtService.generateTokenWithExpiry(authRequest.getUsernameOrEmail());
        return jwtService.generateToken(authRequest.getEmail(), 2);

//        if (authentication.isAuthenticated()) {
//            return jwtService.generateTokenWithExpiry(authRequest.getUsernameOrEmail());
//        } else {
//            throw new UsernameNotFoundException("invalid user request!");
//        }
    }

//    @PostMapping("/generateToken")
//    public String authenticateAndGetToken(@RequestBody SignInDTO authRequest) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsernameOrEmail(), authRequest.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(authRequest.getUsernameOrEmail());
//        } else {
//            throw new UsernameNotFoundException("invalid user request !");
//        }
//    }
}
