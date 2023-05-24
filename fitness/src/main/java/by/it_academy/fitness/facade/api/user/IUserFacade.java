package by.it_academy.fitness.facade.api.user;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.user.AddUserDTO;
import by.it_academy.fitness.core.dto.user.UpdateUserDTO;
import by.it_academy.fitness.core.dto.user.UserDTO;

import java.util.UUID;

public interface IUserFacade<T> {

    void create(AddUserDTO userDTO);

    PageDTO<T> get(int page, int size);

    UserDTO get(UUID id);
    UserDTO getUser(String mail);

    void update(UpdateUserDTO updateUserDto);


}
