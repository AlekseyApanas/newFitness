package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.exception.CheckDoubleException;
import by.it_academy.fitness.core.exception.CheckVersionException;
import by.it_academy.fitness.core.exception.NotFoundException;
import by.it_academy.fitness.dao.api.user.IUserDao;
import by.it_academy.fitness.entity.UserEntity;
import by.it_academy.fitness.service.api.user.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class UserService implements IUserService {

    private final IUserDao dao;
    private final ConversionService conversionService;
    private final PasswordEncoder encoder;

    public UserService(IUserDao dao, ConversionService conversionService, PasswordEncoder encoder) {
        this.dao = dao;
        this.conversionService = conversionService;
        this.encoder = encoder;
    }


    @Override
    public void create(UserEntity user) {
        UserEntity userEntity = dao.findByMail(user.getMail());
        if (userEntity != null) {
            throw new CheckDoubleException("Юзер с таким mail уже существует");
        } else {
            String encode = encoder.encode(user.getPassword());
            user.setPassword(encode);
            dao.save(user);
        }
    }

    @Override
    public Page<UserEntity> get(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        org.springframework.data.domain.Page<UserEntity> all = dao.findAll(paging);
        List<UserEntity> usersPages = all.getContent().stream()
                .map(s -> conversionService.convert(s, UserEntity.class))
                .collect(Collectors.toList());
        return new Page<>(page, size, all.getTotalPages(), all.getTotalElements(), all.isFirst(), all.getNumberOfElements(), all.isLast(), usersPages);
    }


    @Override
    public UserEntity get(UUID uuid) {
        return this.dao.findById(uuid).orElseThrow(() -> new NotFoundException("Такого юзера не существует"));
    }

    @Override
    public void update(UserEntity updateUser) {
        UserEntity userEntity = dao.findById(updateUser.getUuid()).orElseThrow(() -> new NotFoundException("Такого юзера не существует"));
        if (userEntity.getDtUpdate().toEpochMilli() == updateUser.getDtUpdate().toEpochMilli()) {
            userEntity.setMail(updateUser.getMail());
            userEntity.setFio(updateUser.getFio());
            userEntity.setPassword(updateUser.getPassword());
            userEntity.setRole(updateUser.getRole());
            userEntity.setStatus(updateUser.getStatus());
            dao.save(userEntity);
        } else throw new CheckVersionException("Такой версии не существует");
    }

    @Override
    public UserEntity getUser(String mail) {
        UserEntity userEntity = this.dao.findByMail(mail);
        if (userEntity == null) {
            throw new NotFoundException("Такого юзера не существует");
        }
        return userEntity;
    }
}