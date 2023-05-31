package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.dto.user.AddUserDTO;
import by.it_academy.fitness.core.dto.user.UpdateUserDTO;
import by.it_academy.fitness.core.dto.user.UserDTO;
import by.it_academy.fitness.entity.UserEntity;
import by.it_academy.fitness.facade.api.user.IUserFacade;
import by.it_academy.fitness.service.api.user.IUserService;
import org.springframework.core.convert.ConversionService;

import java.util.UUID;

public class FacadeForUser implements IUserFacade {
    private final IUserService iUserService;
    private final ConversionService conversionService;

    public FacadeForUser(IUserService iUserService, ConversionService conversionService) {
        this.iUserService = iUserService;
        this.conversionService = conversionService;
    }

    @Override
    public void create(AddUserDTO userDTO) {
        iUserService.create(conversionService.convert(userDTO, UserEntity.class));
    }

    @Override
    public Page get(int page, int size) {
        return iUserService.get(page, size);
    }

    @Override
    public UserDTO get(UUID id) {
        return conversionService.convert(iUserService.get(id), UserDTO.class);
    }

    @Override
    public void update(UpdateUserDTO updateUserDto) {
        iUserService.update(conversionService.convert(updateUserDto, UserEntity.class));
    }
}
