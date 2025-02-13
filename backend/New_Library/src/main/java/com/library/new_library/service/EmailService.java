package com.library.new_library.service;

import org.springframework.stereotype.Service;

public interface EmailService {
    public boolean sendEmail(String to,String subject,String body);
}
