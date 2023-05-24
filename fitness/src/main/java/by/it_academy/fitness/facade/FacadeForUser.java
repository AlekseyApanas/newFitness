package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.user.AddUserDTO;
import by.it_academy.fitness.core.dto.user.UpdateUserDTO;
import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.facade.api.user.IUserFacade;
import by.it_academy.fitness.service.api.user.IUserService;

import java.util.UUID;

public class FacadeForUser implements IUserFacade {
    private final IUserService iUserService;

    public FacadeForUser(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public void create(AddUserDTO userDTO) {
        iUserService.create(userDTO);
    }

    @Override
    public PageDTO get(int page, int size) {
        return iUserService.get(page, size);
    }

    @Override
    public UserDTO get(UUID id) {
        return iUserService.get(id);
    }

    @Override
    public UserDTO getUser(String mail) {
        return iUserService.getUser(mail);
    }

    @Override
    public void update(UpdateUserDTO updateUserDto) {
        iUserService.update(updateUserDto);
    }
}
