package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class DatabaseRunner implements CommandLineRunner {
    private final UserService userService;
    private final GroupService groupService;

    public DatabaseRunner(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public void run(String... args) {
        User superUser = new User();
        superUser.setUsername("borjan.gjorovski");
        superUser.setEmail("borjan.gjorovski@outlook.com");
        superUser.setFirstName("Borjan");
        superUser.setLastName("Gjorovski");
        superUser.setPassword("12345borjan");

        User user = new User();
        user.setUsername("elena.jovanovska");
        user.setEmail("elena.jovanovska@outlook.com");
        user.setFirstName("Elena");
        user.setLastName("Jovanovska");
        user.setPassword("12345borjan");

        userService.createUser(superUser);
        userService.createUser(user);

        Group userGroup = new Group();
        userGroup.setName("User");
        userGroup.setUsers(List.of(superUser));

        Group adminGroup = new Group();
        adminGroup.setName("Admin");
        adminGroup.setUsers(List.of(superUser));

        groupService.createGroup(userGroup);
        groupService.createGroup(adminGroup);

        superUser.setGroups(List.of(userGroup, adminGroup));
        userService.updateUser(userService.getUserById(superUser.getId()), superUser);

        user.setGroups(List.of(userGroup));
        userService.updateUser(userService.getUserById(user.getId()), user);
    }
}
