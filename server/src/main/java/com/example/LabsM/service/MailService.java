package com.example.LabsM.service;

import com.example.LabsM.jpa.model.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;

import java.util.Random;

@Service
public class MailService {
    @Autowired
    private MailSender mailSender;
    public Integer sendConfirmationCode(String email) {
        System.out.println(email);
        Random random = new Random();
        int code = 1000 + random.nextInt(8999);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("");
        msg.setTo(email);
        msg.setText("Your confirmation code is: " + code);
        msg.setSubject("Confirmation Code");
        try {
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.out.println("Error has happened");
            System.err.println(ex.getMessage());
        }
        return code;
    }

}
