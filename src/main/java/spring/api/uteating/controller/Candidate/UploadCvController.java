package spring.api.uteating.controller.Candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring.api.uteating.entity.Cv;
import spring.api.uteating.service.CloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.ADMIN}")
public class UploadCvController {
    @Autowired
    private CloudinaryService cloudinaryService;



    @PostMapping("/uploadCv")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String email = userDetails.getUsername();
            // Upload file và lưu thông tin vào DB
            Cv uploadedFile = cloudinaryService.uploadFile(file, email);

            // Trả về thông tin file đã lưu trong DB
            return ResponseEntity.ok(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }
}
