package spring.api.uteating.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import spring.api.uteating.repository.UserRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public void sendVerificationEmail(String email) {
        // Tạo token xác minh chứa email
        String token = jwtService.generateTokenWithExpiry(email).get("access_token").toString();

        // Tạo URL xác minh
        String verifyUrl = "http://localhost:8887/api/user/verify?token=" + token;

        // Tạo email
        String subject = "Verify your account";
        String body = "Please click the link below to verify your account: \n" + verifyUrl;

        sendEmail(email, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
