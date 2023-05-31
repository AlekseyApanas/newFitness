package by.it_academy.fitness.config;

import by.it_academy.fitness.dao.api.product.IProductDao;
import by.it_academy.fitness.dao.api.product.IRecipeDao;
import by.it_academy.fitness.dao.api.user.IAuthenticationDao;
import by.it_academy.fitness.dao.api.user.IUserDao;
import by.it_academy.fitness.facade.FacadeForAuthentication;
import by.it_academy.fitness.facade.FacadeForProduct;
import by.it_academy.fitness.facade.FacadeForRecipe;
import by.it_academy.fitness.facade.FacadeForUser;
import by.it_academy.fitness.facade.api.product.IProductFacade;
import by.it_academy.fitness.facade.api.product.IRecipeFacade;
import by.it_academy.fitness.facade.api.user.IAuthenticationFacade;
import by.it_academy.fitness.facade.api.user.IUserFacade;
import by.it_academy.fitness.service.*;
import by.it_academy.fitness.service.api.mail.IEmailService;
import by.it_academy.fitness.service.api.product.IProductService;
import by.it_academy.fitness.service.api.product.IRecipeService;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.service.api.user.IUserService;
import by.it_academy.fitness.web.utils.JwtTokenUtil;
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
    public IAuthenticationFacade iAuthenticationFacade(IAuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, ConversionService conversionService) {
        return new FacadeForAuthentication(authenticationService, jwtTokenUtil, conversionService);
    }

    @Bean
    public IUserFacade iUserFacade(IUserService iUserService, ConversionService conversionService) {
        return new FacadeForUser(iUserService, conversionService);
    }

    @Bean
    public IProductFacade iProductFacade(IProductService iProductService, ConversionService conversionService) {
        return new FacadeForProduct(iProductService, conversionService);
    }

    @Bean
    public IRecipeFacade iRecipeFacade(IRecipeService iRecipeService, ConversionService conversionService) {
        return new FacadeForRecipe(iRecipeService, conversionService);
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
