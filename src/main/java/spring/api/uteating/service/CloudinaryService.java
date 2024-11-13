package spring.api.uteating.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.api.uteating.entity.Cv;
import spring.api.uteating.entity.User;
import spring.api.uteating.repository.CvRepository;
import spring.api.uteating.repository.UserRepository;

import javax.swing.text.Document;
import java.util.Map;

@Service
public class CloudinaryService  {



    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CvRepository cvRepository;


    public Cv uploadFile(MultipartFile file, String email) throws IOException, java.io.IOException {
        // Upload file lên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Tạo đối tượng Document
        Cv cv = Cv.builder()
                .FileName(file.getOriginalFilename())
                .Type(uploadResult.get("format").toString())
                .Cv_id(uploadResult.get("secure_url").toString())
                .build();

        User user  = userRepository.getUserByEmail(email);

        cv.setUser(user);

        return cvRepository.save(cv);
    }





}