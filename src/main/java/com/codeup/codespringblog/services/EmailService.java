package com.codeup.codespringblog.services;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.Post;
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

    @Value("${CUSTOM_KEY}")
    private String customKey;

    public void prepareAndSend(Post post) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(post.getUser().getEmail());
        msg.setSubject("Post Created");
        msg.setText("Post Title: " + post.getTitle() + "\nPost Description: " + post.getBody());

        try{
            this.emailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

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

    public void forgotPassword(String userEmail, String resetPasswordLink) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(userEmail);
        msg.setSubject("Reset your password");
        msg.setText("Dear user,\n\nYou have requested to reset your password. Please click on the following link to reset your password:\n\n" + resetPasswordLink + "\n\nIf you did not request to reset your password, please ignore this email.\n\nThank you,\nBoard Together");

        try {
            emailSender.send(msg);
        } catch (MailException e) {
            System.err.println(e.getMessage());
        }
    }

}
