package com.journey_book_now.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.journey_book_now.service.EmailService;

import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/aero/admin")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-thymeleaf-email")
    public String sendThymeleafEmail(@RequestParam String to, @RequestParam String name) {
        Map<String, Object> variables = new HashMap<>();
		variables.put("name", name);

		emailService.sendEmailWithThymeleaf(to, "Welcome!", "welcome-email", variables);
		return "Email sent successfully!";
    }
}
