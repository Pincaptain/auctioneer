package com.gjorovski.auctioneer.auth.service;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.repository.GroupRepository;
import com.gjorovski.auctioneer.user.model.User;
import com.gjorovski.auctioneer.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, ModelMapper modelMapper, UserService userService) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Group getGroupByName(String name) {
        return groupRepository.findFirstByName(name);
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(long id, Group group) {
        Group currentGroup = getGroupById(id);

        modelMapper.map(group, currentGroup);
        groupRepository.save(currentGroup);

        return currentGroup;
    }

    public Group deleteGroup(long id) {
        Group group = getGroupById(id);
        groupRepository.delete(group);

        return group;
    }

    public Group addUserToGroup(long userId, long groupId) {
        User user = userService.getUserById(userId);
        Group group = getGroupById(groupId);

        group.addUser(user);

        return groupRepository.save(group);
    }

    public Group removeUserFromGroup(long userId, long groupId) {
        User user = userService.getUserById(userId);
        Group group = getGroupById(groupId);

        group.removeUser(user);

        return groupRepository.save(group);
    }
}
