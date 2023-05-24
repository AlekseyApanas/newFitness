package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.core.dto.user.UserRegistrationDTO;
import by.it_academy.fitness.facade.api.user.IAuthenticationFacade;
import by.it_academy.fitness.service.api.user.IAuthenticationService;
import by.it_academy.fitness.web.utils.JwtTokenUtil;

public class FacadeForAuthentication implements IAuthenticationFacade {
    private final IAuthenticationService authenticationService;
    private JwtTokenUtil jwtTokenUtil;

    public FacadeForAuthentication(IAuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDTO logIn(UserLogInDTO userLogInDTO) {
        return authenticationService.logIn(userLogInDTO);
    }

    @Override
    public void registration(UserRegistrationDTO userRegistrationDTO) {
        authenticationService.registration(userRegistrationDTO);
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
