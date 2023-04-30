package com.app.onlineregistrationsystem.service;

import java.util.List;

import com.app.onlineregistrationsystem.dto.UserDto;
import com.app.onlineregistrationsystem.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    String findRoleByEmail(String email);

    List<UserDto> findAllUsers();
}
