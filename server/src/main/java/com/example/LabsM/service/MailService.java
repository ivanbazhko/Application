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
//    private MailSender mailSender;
//    private SimpleMailMessage templateMessage;
//    public void setMailSender() {
//        this.mailSender = new MailSender() {
//            @Override
//            public void send(SimpleMailMessage simpleMessage) throws MailException {
//
//            }
//
//            @Override
//            public void send(SimpleMailMessage... simpleMessages) throws MailException {
//
//            }
//        };
//    }
//    public void setTemplateMessage(String email) {
//        this.templateMessage = new SimpleMailMessage();
//        this.templateMessage.setCc("thebestairportever@gmail.com");
//        this.templateMessage.setSubject("Confirmation Code");
//    }
    @Autowired
    private MailSender mailSender;
    public void sendConfirmationCode(String email) {
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
    }

}
