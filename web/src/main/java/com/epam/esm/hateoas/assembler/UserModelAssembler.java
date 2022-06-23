package com.epam.esm.hateoas.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        UserModel userModel = createModelWithId(entity.getId(), entity);
        userModel.setId(entity.getId());
        userModel.setName(entity.getName());
        userModel.setEmail(entity.getEmail());
        userModel.setRole(entity.getRole());
        userModel.setStatus(entity.getStatus());
        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserDto> entities) {
        List<UserModel> userModels = new ArrayList<>();
        entities.forEach(tagDto -> userModels.add(toModel(tagDto)));
        return CollectionModel.of(userModels);
    }
}
