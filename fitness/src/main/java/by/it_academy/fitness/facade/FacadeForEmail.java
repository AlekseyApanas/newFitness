package by.it_academy.fitness.facade;

import by.it_academy.fitness.facade.api.mail.IEmailFacade;
import by.it_academy.fitness.service.api.mail.IEmailService;

public class FacadeForEmail implements IEmailFacade {
    private final IEmailService emailService;

    public FacadeForEmail(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        emailService.sendSimpleEmail(toAddress, subject, message);
    }
}
