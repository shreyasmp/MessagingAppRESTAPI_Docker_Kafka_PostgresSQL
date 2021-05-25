package com.shreyas.messaging.util;

import com.shreyas.messaging.data.UserDTO;
import com.shreyas.messaging.exception.EmptyOrInvalidInputException;
import com.shreyas.messaging.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserUtil {

    public UserDTO modelToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(Long.toString(user.getId()));
        userDTO.setName(user.getName());
        return userDTO;
    }

    public List<UserDTO> modelToDTO(List<User> users) {
        return users.stream().map(this::modelToDTO).collect(Collectors.toList());
    }

    public User dtoToModel(UserDTO userDTO) {
        User user = new User();
        user.setId((userDTO.getId() == null || userDTO.getId().isEmpty()) ? 0 : Long.parseLong(userDTO.getId()));
        if (userDTO.getName() == null || userDTO.getName().isEmpty())
            throw new EmptyOrInvalidInputException("601", "Name cannot be null or empty!");
        else
            user.setName(userDTO.getName());

        return user;
    }

    public List<User> dtoToModel(List<UserDTO> users) {
        return users.stream().map(this::dtoToModel).collect(Collectors.toList());
    }
}
