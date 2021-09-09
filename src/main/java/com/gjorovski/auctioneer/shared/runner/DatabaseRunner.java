package com.gjorovski.auctioneer.shared.runner;

import com.gjorovski.auctioneer.auction.model.Auction;
import com.gjorovski.auctioneer.auction.model.Item;
import com.gjorovski.auctioneer.auction.model.Lot;
import com.gjorovski.auctioneer.auction.service.AuctionService;
import com.gjorovski.auctioneer.auction.service.ItemService;
import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final AuctionService auctionService;

    private final Logger logger = LoggerFactory.getLogger(DatabaseRunner.class);

    public DatabaseRunner(UserService userService, GroupService groupService, ItemService itemService, AuctionService auctionService) {
        this.userService = userService;
        this.groupService = groupService;
        this.itemService = itemService;
        this.auctionService = auctionService;
    }

    @Override
    public void run(String... args) {
        createUsersAndGroups();
        createItemsAndAuctions();
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

    private void createItemsAndAuctions() {
        User user = userService.getUserById(1);
        User secondUser = userService.getUserById(2);

        Item item = new Item();
        item.setName("Statue of Liberty");
        item.setDetails("The Statue of Liberty is a 305-foot (93-metre) statue located on Liberty Island in Upper New York Bay, off the coast of New York City. The statue is a personification of liberty in the form of a woman. She holds a torch in her raised right hand and clutches a tablet in her left.");
        item.setValue(250315);

        Item anotherItem = new Item();
        anotherItem.setName("Eiffel Tower");
        anotherItem.setDetails("The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France. It is named after the engineer Gustave Eiffel, whose company designed and built the tower.");
        anotherItem.setValue(300250);

        Item createdItem = itemService.createItem(item);
        Item anotherCreatedItem = itemService.createItem(anotherItem);

        Lot lot = new Lot();
        lot.setCount(1);
        lot.setItem(createdItem);
        lot.setStartingPrice(200000);
        lot.setSeller(user);
        lot.setCurrentBid(210000);
        lot.setHighestBidder(secondUser);

        Lot anotherLot = new Lot();
        anotherLot.setCount(2);
        anotherLot.setItem(anotherCreatedItem);
        anotherLot.setStartingPrice(300000);
        anotherLot.setSeller(user);
        anotherLot.setCurrentBid(310000);
        anotherLot.setHighestBidder(secondUser);

        Auction auction = new Auction();
        auction.setName("North Hills Auction");
        auction.setDetails("North Hills testing groups virtual auction house.");
        auction.setOwner(user);

        Auction createdAuction = auctionService.createAuction(auction);

        auctionService.createAuctionLot(createdAuction.getId(), lot);
        auctionService.createAuctionLot(createdAuction.getId(), anotherLot);
    }
}
