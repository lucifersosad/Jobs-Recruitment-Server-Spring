package spring.api.uteating.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.api.uteating.entity.Candidate;
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
    private CandidateService candidateService;

    @Autowired
    private CvService cvService;


    public Cv uploadFile(MultipartFile file, String email) throws IOException, java.io.IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        Cv cv = Cv.builder()
                .cvId(uploadResult.get("public_id").toString())
                .name(file.getOriginalFilename())
                .type(uploadResult.get("format").toString())
                .originUrl(uploadResult.get("secure_url").toString())
                .build();

        Candidate candidate = candidateService.getOneByEmail(email);

        cv.setCandidate(candidate);

        return cvService.save(cv);
    }





}