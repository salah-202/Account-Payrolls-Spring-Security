package com.example.accountpayrolls.entities;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Named("mapRoleName")
    default String mapRoleName(Role role){
        return role.getName();
    }
    @Mapping(source = "roles",target = "roles",qualifiedByName = "mapRoleName")
    UserDto appUserToUserDto(AppUser appUser);
}
