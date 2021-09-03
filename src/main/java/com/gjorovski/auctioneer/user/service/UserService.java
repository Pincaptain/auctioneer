package com.gjorovski.auctioneer.user.service;

import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public User getUserByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public User updateUser(User currentUser, User user) {
        modelMapper.map(user, currentUser);
        userRepository.save(currentUser);

        return currentUser;
    }

    public User updatePassword(User user, String password, String newPassword) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PermissionDeniedException("User password doesn't match with the provided one.");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        return user;
    }

    public User updateUsername(User user, String username) {
        user.setUsername(username);
        userRepository.save(user);

        return user;
    }

    public User updateEmail(User user, String email) {
        user.setEmail(email);
        userRepository.save(user);

        return user;
    }

    public User deleteUser(User user) {
        userRepository.delete(user);

        return user;
    }
}