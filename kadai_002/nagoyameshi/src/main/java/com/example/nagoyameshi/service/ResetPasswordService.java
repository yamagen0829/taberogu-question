package com.example.nagoyameshi.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.PasswordResetDto;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ResetPasswordService {
	 @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private VerificationTokenService verificationTokenService;
	    
	    @Autowired
	    private JavaMailSender mailSender;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Transactional
	    public void sendResetToken(String email) {
	        User user = userRepository.findByEmail(email);
	        if (user != null) {
	            String token = UUID.randomUUID().toString();
	            verificationTokenService.update(user, token);
	            
	         // デバッグログ追加
	            System.out.println("Generated Token: " + token);
	            
	            String resetUrl = "http://localhost:8080/resetpassword?token=" + token;
	            
	            try {
	                sendEmail(email, resetUrl);
	             // メール送信確認
	                System.out.println("Email sent to: " + email + " with url: " + resetUrl);
	          
	            } catch (MessagingException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    private void sendEmail(String to, String resetUrl) throws MessagingException {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(to);
	        helper.setSubject("パスワードリセット");
	        helper.setText("以下のリンクをクリックしてパスワードをリセットしてください\n" + resetUrl, true);
	        mailSender.send(message);
	        
	     // メール送信ログ追加
	        System.out.println("Email sent to: " + to);
	    }
	    
	    // トークン検証
	    public boolean validateResetPasswordToken(String token) {
	        return verificationTokenService.isTokenValid(token);
	    }
	    
	    @Transactional
	    public void updatePassword(PasswordResetDto form) {
	        VerificationToken verificationToken = verificationTokenService.getVerificationToken(form.getToken());
	        if (verificationToken != null) {
	            User user = verificationToken.getUser();
	            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
	            verificationTokenService.deleteToken(verificationToken);
	            userRepository.save(user);
	        } 
	    }
	}