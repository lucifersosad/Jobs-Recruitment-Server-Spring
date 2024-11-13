package spring.api.uteating.controller.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spring.api.uteating.entity.Cv;
import spring.api.uteating.service.CloudinaryService;
import spring.api.uteating.utils.ResponseUtil;

import java.security.Principal;

@RestController
@RequestMapping("${api.BASE_URL}${prefix.CANDIDATE}/cv")
public class CvController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file, Principal principal) {
        try {
            String email = principal.getName();
            Cv uploadedFile = cloudinaryService.uploadFile(file, email);
            return ResponseUtil.successResponse(uploadedFile, "Upload CV thành công");
        } catch (Exception e) {
            return ResponseUtil.errorResponse("Upload CV thất bại", HttpStatus.BAD_REQUEST);
        }
    }
}
