package by.it_academy.fitness.config;

import by.it_academy.fitness.core.dto.user.AddUserDTO;
import by.it_academy.fitness.dao.api.product.IProductDao;
import by.it_academy.fitness.dao.api.product.IRecipeDao;
import by.it_academy.fitness.dao.api.user.IAuthenticationDao;
import by.it_academy.fitness.dao.api.user.IUserDao;
import by.it_academy.fitness.service.*;
import by.it_academy.fitness.service.api.mail.IEmailService;
import by.it_academy.fitness.service.api.product.IProductService;
import by.it_academy.fitness.service.api.product.IRecipeService;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.service.api.user.IUserService;
import by.it_academy.fitness.userEnum.UserRole;
import by.it_academy.fitness.userEnum.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Properties;


@Configuration
public class SpringConfig {
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserService userService(IUserDao dao, ConversionService conversionService, PasswordEncoder encoder) {
        return new UserService(dao, conversionService, encoder);
    }

    @Bean
    public IAuthenticationService authenticationService(IAuthenticationDao dao, ConversionService conversionService, IEmailService iEmailService, BCryptPasswordEncoder encoder, IUserService iUserService) {
        return new AuthenticationService(dao, conversionService, iEmailService, encoder, iUserService);
    }

    @Bean
    public IProductService productService(IProductDao dao, ConversionService conversionService) {
        return new ProductService(dao, conversionService);
    }

    @Bean
    public IRecipeService recipeService(IRecipeDao dao, IProductService productService, ConversionService conversionService) {
        return new RecipeService(dao, productService, conversionService);
    }

    @Bean
    public IEmailService emailService(JavaMailSender emailSender) {
        return new EmailService(emailSender);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(465);
        mailSender.setUsername("alsha.alsha.23@mail.ru");
        mailSender.setPassword("CGtwWNqerZXfEYbM2Hyq");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
