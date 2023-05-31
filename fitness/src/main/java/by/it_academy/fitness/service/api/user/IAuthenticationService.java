package by.it_academy.fitness.service.api.user;

import by.it_academy.fitness.entity.UserEntity;

public interface IAuthenticationService {
    UserEntity logIn(UserEntity userEntity);

    void registration(UserEntity userEntity);

    void verification(String code,String mail);

}
