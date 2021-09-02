package com.gjorovski.auctioneer.user;

import com.gjorovski.auctioneer.auth.Authentication;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import com.gjorovski.auctioneer.user.request.*;
import com.gjorovski.auctioneer.user.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getUsers();
        List<UserResponse> userResponses = users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);
        User createdUser = userService.createUser(user);
        UserResponse createdUserResponse = modelMapper.map(createdUser, UserResponse.class);

        return new ResponseEntity<>(createdUserResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        User user = modelMapper.map(updateUserRequest, User.class);
        User updatedUser = userService.updateUser(authentication.getUser(), user);
        UserResponse updatedUserResponse = modelMapper.map(updatedUser, UserResponse.class);

        return new ResponseEntity<>(updatedUserResponse, HttpStatus.OK);
    }

    @PutMapping("password")
    public ResponseEntity<UserResponse> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        String password = updatePasswordRequest.getPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        User user = userService.updatePassword(authentication.getUser(), password, newPassword);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("username")
    public ResponseEntity<UserResponse> updateUsername(@RequestBody @Valid UpdateUsernameRequest updateUsernameRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        User user = userService.updateUsername(authentication.getUser(), updateUsernameRequest.getUsername());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("email")
    public ResponseEntity<UserResponse> updateEmail(@RequestBody @Valid UpdateEmailRequest updateEmailRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        User user = userService.updateEmail(authentication.getUser(), updateEmailRequest.getEmail());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<UserResponse> deleteUser(@RequestAttribute Authentication authentication) {
        if (!authentication.isAuthenticated()) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        User deletedUser = userService.deleteUser(authentication.getUser());
        UserResponse deletedUserResponse = modelMapper.map(deletedUser, UserResponse.class);

        return new ResponseEntity<>(deletedUserResponse, HttpStatus.OK);
    }
}
