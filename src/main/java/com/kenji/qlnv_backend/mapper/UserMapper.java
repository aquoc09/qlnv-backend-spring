package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.UserCreationRequest;
import com.kenji.qlnv_backend.dto.request.UserUpdateRequest;
import com.kenji.qlnv_backend.dto.response.UserResponse;
import com.kenji.qlnv_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Mapping(target = "roles", source = "roles")
    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
