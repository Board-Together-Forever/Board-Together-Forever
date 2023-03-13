package com.codeup.codespringblog.services;

import com.codeup.codespringblog.models.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("no-reply@boardtogether.org")
    private String from;

    public void prepareAndSend(GameSession gameSession) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(gameSession.getGameSessionHost().getEmail());
        msg.setSubject("Post Created");
        msg.setText("Post Title: " + gameSession.getGameSessionTitle() + "\nPost Description: " + gameSession.getGameSessionDescription());

        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
