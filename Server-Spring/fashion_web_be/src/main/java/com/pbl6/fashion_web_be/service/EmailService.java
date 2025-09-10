package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.entity.UserAccount;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private static final String SENDER_NAME = "Shop HEAVEN-CLOTHING";

    /**
     * Gửi email xác thực tới user
     */
    public void sendVerificationEmail(UserAccount user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("Verify your email");
            helper.setFrom(sender, SENDER_NAME);
            helper.setTo(user.getEmail());
            helper.setText(buildVerificationEmailContent(user), true);

            mailSender.send(message);
            log.info("Verification email sent to {}", user.getEmail());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    /**
     * Tạo nội dung HTML cho email
     */
    private String buildVerificationEmailContent(UserAccount user) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Verify Your Email</title>
                <style>
                    body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
                    .container { max-width: 600px; margin: 30px auto; background: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { padding: 30px; text-align: center; }
                    .content h2 { color: #333; }
                    .code { font-size: 28px; color: #FF6347; font-weight: bold; margin: 20px 0; }
                    .btn { display: inline-block; padding: 12px 25px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; }
                    .footer { font-size: 12px; color: #888; padding: 20px; text-align: center; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>HEAVEN-CLOTHING</h1>
                    </div>
                    <div class="content">
                        <h2>Hello %s,</h2>
                        <p>Your verification code is:</p>
                        <div class="code">%s</div>
                        <p>Please enter this code to verify your email address.</p>
                        <a href="#" class="btn">Verify Now</a>
                    </div>
                    <div class="footer">
                        Best regards, <br> HEAVEN-CLOTHING Team
                    </div>
                </div>
            </body>
            </html>
            """, user.getUsername(), user.getVerificationCode());
    }

}
