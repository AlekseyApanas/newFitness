package by.it_academy.fitness.service.api.mail;


public interface IEmailService {
    public void sendSimpleEmail(String toAddress, String subject, String message);
}
