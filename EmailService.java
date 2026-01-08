package com.TaskMange.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sentResetEMail(String to,String token) {
		
		String resetToken="http://localhost:5050//api/Authentication/rest_password?token="+token;
		
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(to);
		message.setSubject("Reset your password");
		message.setText("Click the link toreset your password:\n"+ resetToken);
		
		mailSender.send(message);
	}

}

