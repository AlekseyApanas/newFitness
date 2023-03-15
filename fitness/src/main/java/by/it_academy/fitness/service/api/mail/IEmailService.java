package by.it_academy.fitness.service.api.mail;

import org.springframework.stereotype.Repository;

public interface IEmailService {
    public void sendSimpleEmail(String toAddress, String subject, String message);
}
