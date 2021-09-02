package com.gjorovski.auctioneer.auth.service;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupService(GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(long id) {
        return groupRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Group getGroupByName(String name) {
        return groupRepository.findFirstByName(name);
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(long id, Group group) {
        Group currentGroup = getGroup(id);

        modelMapper.map(group, currentGroup);
        groupRepository.save(currentGroup);

        return currentGroup;
    }

    public Group deleteGroup(long id) {
        Group group = getGroup(id);
        groupRepository.delete(group);

        return group;
    }
}
