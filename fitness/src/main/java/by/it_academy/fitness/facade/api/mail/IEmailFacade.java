package by.it_academy.fitness.facade.api.mail;


public interface IEmailFacade {
    public void sendSimpleEmail(String toAddress, String subject, String message);
}
