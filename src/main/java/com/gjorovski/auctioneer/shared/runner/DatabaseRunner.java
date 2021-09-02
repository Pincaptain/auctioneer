package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

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

        userService.createUser(superUser);

        Group userGroup = new Group();
        userGroup.setName("User");
        userGroup.setUsers(Set.of(superUser));

        Group adminGroup = new Group();
        adminGroup.setName("Admin");
        adminGroup.setUsers(Set.of(superUser));

        groupService.createGroup(userGroup);
        groupService.createGroup(adminGroup);

        superUser.setGroups(Set.of(userGroup, adminGroup));

        userService.updateUser(userService.getUserById(superUser.getId()), superUser);
    }
}
