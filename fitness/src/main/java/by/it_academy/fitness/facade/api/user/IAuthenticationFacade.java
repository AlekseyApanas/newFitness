package by.it_academy.fitness.facade.api.user;

import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.core.dto.user.UserRegistrationDTO;
import by.it_academy.fitness.web.utils.JwtTokenUtil;

public interface IAuthenticationFacade {
    UserDTO logIn(UserLogInDTO userLogInDTO);

    void registration(UserRegistrationDTO userRegistrationDTO);

    void verification(String code, String mail);

    JwtTokenUtil getToken();
}
