package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.user.AddUserDTO;
import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.core.dto.user.UserRegistrationDTO;
import by.it_academy.fitness.core.exception.CheckDoubleException;
import by.it_academy.fitness.core.exception.NotFoundException;
import by.it_academy.fitness.core.exception.ValidException;
import by.it_academy.fitness.dao.api.user.IAuthenticationDao;
import by.it_academy.fitness.entity.StatusEntity;
import by.it_academy.fitness.entity.UserEntity;
import by.it_academy.fitness.service.api.mail.IEmailService;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.service.api.user.IUserService;
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

    public UserDTO logIn(UserLogInDTO userLogInDTO) {
        UserEntity userEntity = dao.findByMail(userLogInDTO.getMail());
        if (userEntity == null) {
            throw new NotFoundException("Такого юзера не существует");
        }
        try {
            encoder.matches(userLogInDTO.getPassword(), userEntity.getPassword());
        } catch (RuntimeException e) {
            throw new ValidException("Введены некорректные данные");
        }
        return conversionService.convert(userEntity, UserDTO.class);
    }


    @Override
    public void registration(UserRegistrationDTO userRegistrationDTO) {
        UserEntity userEntity = dao.findByMail(userRegistrationDTO.getMail());
        if (userEntity != null) {
            throw new CheckDoubleException("Юзер с таким mail уже существует");
        } else {
            iUserService.create(new AddUserDTO(userRegistrationDTO.getMail(), userRegistrationDTO.getFio(), userRegistrationDTO.getPassword()));
            UUID code = UUID.randomUUID();
            userEntity = dao.findByMail(userRegistrationDTO.getMail());
            userEntity.setCode(code.toString());
            dao.save(userEntity);
            emailService.sendSimpleEmail(userRegistrationDTO.getMail(), "Verification", code.toString());
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
