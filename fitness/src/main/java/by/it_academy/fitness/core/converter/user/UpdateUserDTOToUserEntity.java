package by.it_academy.fitness.core.converter.user;

import by.it_academy.fitness.core.dto.user.UpdateUserDTO;
import by.it_academy.fitness.entity.RoleEntity;
import by.it_academy.fitness.entity.StatusEntity;
import by.it_academy.fitness.entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class UpdateUserDTOToUserEntity implements Converter<UpdateUserDTO, UserEntity> {
    @Override
    public UserEntity convert(UpdateUserDTO source) {
        return new UserEntity(source.getUuid(),
                source.getUserDTO().getMail(),
                source.getUserDTO().getFio(),
                source.getUserDTO().getPassword(),
                source.getDtUpdate(),
                new RoleEntity(source.getUserDTO().getRole()),
                new StatusEntity(source.getUserDTO().getStatus()));
    }
}
