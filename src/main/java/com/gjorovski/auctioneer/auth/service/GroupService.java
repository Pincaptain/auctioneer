package com.gjorovski.auctioneer.auth.service;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.repository.GroupRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
}
