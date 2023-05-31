package by.it_academy.fitness.core.converter.user;

import by.it_academy.fitness.core.dto.user.UserLogInDTO;
import by.it_academy.fitness.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserLoginDTOTOUserEntity implements Converter<UserLogInDTO, UserEntity> {
    @Override
    public UserEntity convert(UserLogInDTO source) {
        return new UserEntity(source.getMail(), source.getPassword());
    }
}
