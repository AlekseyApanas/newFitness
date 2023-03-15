package by.it_academy.fitness.service;

import by.it_academy.fitness.service.api.mail.IEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService implements IEmailService {
    public JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendSimpleEmail(String toAddress, String subject, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ivanivanov2023_18@mail.ru");
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText("Для подтверждения перейдите по ссылке :http://localhost:8080/api/v1/users/verification?code=" + code + "&mail=" + toAddress);
        emailSender.send(simpleMailMessage);
    }
}
