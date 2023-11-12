package com.localservicesreview.notificationservice.consumer;

import jakarta.activation.URLDataSource;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public Boolean sendEmail(String to, String subject, String body, String imageURL){
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            MimeMailMessage mimeMailMessage = new MimeMailMessage(mimeMessageHelper);

            if(imageURL!=null){
                URL url = new URL(imageURL);
                URLDataSource dataSource = new URLDataSource(url);
                mimeMessageHelper.addAttachment("image",dataSource);
                body+= "<br><br><img src='cid:image' width='100%' height='100%'>";
            }

            mimeMailMessage.setFrom(fromEmail);
            mimeMailMessage.setTo(to);
            mimeMailMessage.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body,"text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mimeMailMessage.getMimeMessage().setContent(multipart);

            javaMailSender.send(mimeMailMessage.getMimeMessage());

//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom(fromEmail);
//            simpleMailMessage.setTo(to);
//            simpleMailMessage.setSubject(subject);
//            simpleMailMessage.setText(body);
//            javaMailSender.send(simpleMailMessage);

            return true;
        }catch(Exception e){
            System.out.println("Exception occurred while sending email: "+e.getMessage());
            return false;
        }
    }
}
