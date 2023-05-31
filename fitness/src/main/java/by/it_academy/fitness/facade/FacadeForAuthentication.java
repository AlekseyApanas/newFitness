package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.core.dto.user.UserRegistrationDTO;
import by.it_academy.fitness.entity.UserEntity;
import by.it_academy.fitness.facade.api.user.IAuthenticationFacade;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.web.utils.JwtTokenUtil;
import org.springframework.core.convert.ConversionService;

public class FacadeForAuthentication implements IAuthenticationFacade {
    private final IAuthenticationService authenticationService;
    private JwtTokenUtil jwtTokenUtil;
    private final ConversionService conversionService;

    public FacadeForAuthentication(IAuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, ConversionService conversionService) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.conversionService = conversionService;
    }


    @Override
    public UserDTO logIn(UserLogInDTO userLogInDTO) {
        UserEntity userEntity=authenticationService.logIn(conversionService.convert(userLogInDTO, UserEntity.class));
        return conversionService.convert(userEntity, UserDTO.class);
    }

    @Override
    public void registration(UserRegistrationDTO userRegistrationDTO) {
        authenticationService.registration(conversionService.convert(userRegistrationDTO, UserEntity.class));
    }

    @Override
    public void verification(String code, String mail) {
        authenticationService.verification(code, mail);
    }

    @Override
    public JwtTokenUtil getToken() {
        return jwtTokenUtil;
    }
}
