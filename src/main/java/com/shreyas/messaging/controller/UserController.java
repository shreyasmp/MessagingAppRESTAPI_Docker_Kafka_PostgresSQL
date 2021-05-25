package com.shreyas.messaging.controller;

import com.shreyas.messaging.data.UserDTO;
import com.shreyas.messaging.exception.DuplicateEntryException;
import com.shreyas.messaging.exception.EmptyOrInvalidInputException;
import com.shreyas.messaging.model.User;
import com.shreyas.messaging.service.UserService;
import com.shreyas.messaging.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messaging")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userUtil;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = null;
        if (userDTO.getName() == null || userDTO.getName().isEmpty())
            throw new EmptyOrInvalidInputException("601", "Invalid input! Value cannot be null or empty!");
        else {
            User newUser = userUtil.dtoToModel(userDTO);
            User userFromDB = userService.getUserByName(newUser.getName());
            if (userFromDB == null) {
                userFromDB = userService.saveUser(newUser);
                createdUser = userUtil.modelToDTO(userFromDB);
                logger.info("User Created Successfully");
            } else {
                throw new DuplicateEntryException("602", "An item with the same name already exists!");
            }
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Getting All Users ....");
        List<UserDTO> users = userUtil.modelToDTO(userService.getAllUsers());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        User userFromDB = userService.getUserById(id);
        UserDTO user = userUtil.modelToDTO(userFromDB);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        logger.info("User Deleted Successfully");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
