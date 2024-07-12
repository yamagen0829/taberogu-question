package com.example.nagoyameshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.PasswordResetDto;
import com.example.nagoyameshi.service.ResetPasswordService;

@Controller
public class ResetPasswordController {
    @Autowired
    private ResetPasswordService resetPasswordService;
	

    @GetMapping("/resetrequest")
    public String showResetRequestForm () {
   	 return "auth/resetrequest";
    }
    
    @PostMapping("/resetPassword/request")
    public String handleResetRequest(@RequestParam("email") String email, Model model) {
        resetPasswordService.sendResetToken(email);
        model.addAttribute("message", "パスワードリセットリンクを送信しました。メールを確認してください。");
        return "auth/resetrequest";
    }
    
	@GetMapping("/resetPassword")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
	    if (!resetPasswordService.validateResetPasswordToken(token)) {
	        model.addAttribute("message", "無効なトークンです。");
	        return "redirect:/login?error=true";
	    }
	    model.addAttribute("token", token);
	    return "auth/resetpassword";
	}
	@PostMapping("/resetPassword")
	public String handleResetPassword(@ModelAttribute PasswordResetDto form, Model model) {
	    resetPasswordService.updatePassword(form);
	    return "redirect:/login?resetSuccess=true";
	}
}
