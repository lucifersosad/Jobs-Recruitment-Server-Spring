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
import spring.api.uteating.service.UserServiceImpl;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserServiceImpl userService;


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