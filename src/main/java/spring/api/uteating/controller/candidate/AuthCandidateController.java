package spring.api.uteating.controller.candidate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.api.uteating.dto.LoginDTO;
import spring.api.uteating.entity.Candidate;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.model.EmployerModel;
import spring.api.uteating.model.UserModel;
import spring.api.uteating.repository.CandidateRepository;
import spring.api.uteating.repository.EmployerRepository;
import spring.api.uteating.service.JwtService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.CANDIDATE}/auth")
public class AuthCandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployer(@RequestBody LoginDTO loginDTO) {

        Optional<Candidate> opt = candidateRepository.findByEmail(loginDTO.getEmail());

        if (opt.isEmpty()) {
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

        Candidate candidate = opt.get();
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(candidate, userModel);
        userModel.setId(candidate.getUserId());

        response.put("info", userModel);

        return ResponseEntity.ok(response);
    }
}
