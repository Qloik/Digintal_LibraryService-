package com.library.new_library.service.impl;

import com.library.new_library.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to,String subject,String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("3259965899@qq.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try
        {
            mailSender.send(message);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
