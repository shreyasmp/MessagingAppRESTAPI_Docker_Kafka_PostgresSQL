package com.shreyas.messaging.service;

import com.shreyas.messaging.model.User;
import com.shreyas.messaging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get();
        }
        return null;
    }

    public User getUserByName(String userName) {
        if (userRepository.findByName(userName).isPresent()) {
            return userRepository.findByName(userName).get();
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
