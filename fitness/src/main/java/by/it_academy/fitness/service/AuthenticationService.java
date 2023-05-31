package by.it_academy.fitness.service;

import by.it_academy.fitness.core.exception.CheckDoubleException;
import by.it_academy.fitness.core.exception.NotFoundException;
import by.it_academy.fitness.core.exception.ValidException;
import by.it_academy.fitness.dao.api.user.IAuthenticationDao;
import by.it_academy.fitness.entity.RoleEntity;
import by.it_academy.fitness.entity.StatusEntity;
import by.it_academy.fitness.entity.UserEntity;
import by.it_academy.fitness.service.api.mail.IEmailService;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.service.api.user.IUserService;
import by.it_academy.fitness.userEnum.UserRole;
import by.it_academy.fitness.userEnum.UserStatus;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class AuthenticationService implements IAuthenticationService {
    private final IAuthenticationDao dao;
    private final ConversionService conversionService;
    private final IEmailService emailService;
    private final BCryptPasswordEncoder encoder;
    private final IUserService iUserService;

    public AuthenticationService(IAuthenticationDao dao, ConversionService conversionService, IEmailService emailService, BCryptPasswordEncoder encoder, IUserService iUserService) {
        this.dao = dao;
        this.conversionService = conversionService;
        this.emailService = emailService;
        this.encoder = encoder;
        this.iUserService = iUserService;
    }

    public UserEntity logIn(UserEntity user) {
        UserEntity userEntity = dao.findByMail(user.getMail());
        if (userEntity == null) {
            throw new NotFoundException("Такого юзера не существует");
        }
        try {
            encoder.matches(user.getPassword(), userEntity.getPassword());
        } catch (RuntimeException e) {
            throw new ValidException("Введены некорректные данные");
        }
        return userEntity;
    }


    @Override
    public void registration(UserEntity user) {
        UserEntity userEntity = dao.findByMail(user.getMail());
        if (userEntity != null) {
            throw new CheckDoubleException("Юзер с таким mail уже существует");
        } else {
            iUserService.create(new UserEntity(user.getMail(), user.getFio(), user.getPassword()));
            UUID code = UUID.randomUUID();
            userEntity = dao.findByMail(user.getMail());
            userEntity.setCode(code.toString());
            userEntity.setRole(new RoleEntity(UserRole.USER));
            userEntity.setStatus(new StatusEntity(UserStatus.WAITING_ACTIVATION));
            dao.save(userEntity);
            emailService.sendSimpleEmail(user.getMail(), "Verification", code.toString());
        }
    }

    @Override
    public void verification(String code, String mail) {
        UserEntity userEntity = dao.findByMail(mail);
        if (userEntity != null && code.equals(userEntity.getCode())) {
            userEntity.setStatus(new StatusEntity((UserStatus.ACTIVATED)));
            userEntity.setCode(null);
            dao.save(userEntity);
        } else throw new NotFoundException("Такого юзера не существует");
    }
}
