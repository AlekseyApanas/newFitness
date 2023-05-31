package by.it_academy.fitness.service.api.user;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.entity.UserEntity;


import java.util.UUID;

public interface IUserService<T> {

    void create(UserEntity userEntity);

    Page<T> get(int page, int size);

    UserEntity get(UUID id);
    UserEntity getUser(String mail);

    void update(UserEntity userEntity);


}
