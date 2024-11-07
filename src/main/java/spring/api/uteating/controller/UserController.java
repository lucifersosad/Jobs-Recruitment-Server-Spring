package spring.api.uteating.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import spring.api.uteating.entity.User;
import spring.api.uteating.model.*;
import spring.api.uteating.repository.UserRepository;
import spring.api.uteating.service.EmailService;
import spring.api.uteating.service.JwtService;
import spring.api.uteating.service.UserServiceImpl;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserServiceImpl userService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("Welcome user");
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Unauthorized");
            response.put("statusCode", 401);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/get-email-from-token")
    public ResponseEntity<String> getEmailFromToken(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Lấy token từ header Authorization (có dạng "Bearer <token>")
            String token = authorizationHeader.replace("Bearer ", "").trim();

            if (token.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is required");
            }

            // Lấy email từ token
            String email = jwtService.extractEmailFromToken(token);

            return ResponseEntity.ok(email);  // Trả về email đã trích xuất từ token
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
    @PostMapping("/send-verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Lấy token từ header Authorization (có dạng "Bearer <token>")
            String token = authorizationHeader.replace("Bearer ", "").trim();

            if (token.isEmpty()) {
                return ResponseEntity.status(400).body("Token is required");
            }

            // Lấy email từ token
            String email = jwtService.extractEmailFromToken(token);

            // Gửi email xác minh
            emailService.sendVerificationEmail(email);

            return ResponseEntity.ok("Verification email sent!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send verification email.");
        }
    }



    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        try {
            // Trích xuất email từ token
            String email = jwtService.extractEmailFromToken(token);

            // Kiểm tra xem người dùng có tồn tại không
            User user = userRepository.getUserByUsernameOrEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found.");
            }

            // Cập nhật trạng thái xác minh của người dùng
            user.setVertified(true);
            userRepository.save(user);

            return ResponseEntity.ok("Account verified successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    boolean isAuthorityUser(String userId, Authentication authentication) {
        String username = authentication.getName();
        User authUser = userService.getUser(username);
        return authUser.getUserId().equals(Long.parseLong(userId));
    }

    @GetMapping("/authority/{userid}")
    public ResponseEntity<?> authority(@PathVariable ("userid") String userId, Authentication authentication) {
        try {
            if (!isAuthorityUser(userId, authentication)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorReponse("Ban khong duoc cap quyen"));
            } else {
                return ResponseEntity.ok(userService.getUserModel(userId));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorReponse(e.getMessage()));
        }
    }

    @GetMapping("/{userid}")
    public UserModel showInfornation(@PathVariable ("userid") String userId) {
        return userService.getUserModel(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel) {
        try {
            UserModel updatedUser = userService.updateUser(userModel);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}