package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.ItemService;
import com.gjorovski.auctioneer.auction.service.LotService;
import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class DatabaseRunner implements CommandLineRunner {
    private final UserService userService;
    private final GroupService groupService;
    private final ItemService itemService;
    private final LotService lotService;

    public DatabaseRunner(UserService userService, GroupService groupService, ItemService itemService, LotService lotService) {
        this.userService = userService;
        this.groupService = groupService;
        this.itemService = itemService;
        this.lotService = lotService;
    }

    @Override
    public void run(String... args) {
        createUsersAndGroups();
        createItems();
    }

    private void createUsersAndGroups() {
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

    private void createItems() {
        Item item = new Item();
        item.setName("Statue of Liberty");
        item.setDetails("The Statue of Liberty is a 305-foot (93-metre) statue located on Liberty Island in Upper New York Bay, off the coast of New York City. The statue is a personification of liberty in the form of a woman. She holds a torch in her raised right hand and clutches a tablet in her left.");
        item.setValue(250312);

        Item createdItem = itemService.createItem(item);

        Lot lot = new Lot();
        lot.setCount(1);
        lot.setItem(createdItem);
        lot.setStartingPrice(200000);

        Lot createdLot = lotService.createLot(lot);

        System.out.printf("%s created right now!", createdLot.getItem().getName());
    }
}
