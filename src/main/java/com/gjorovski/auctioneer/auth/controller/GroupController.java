package com.gjorovski.auctioneer.auth.controller;

import com.gjorovski.auctioneer.auth.domain.Authentication;
import com.gjorovski.auctioneer.auth.domain.GroupType;
import com.gjorovski.auctioneer.auth.request.AddUserToGroupRequest;
import com.gjorovski.auctioneer.auth.request.RemoveUserFromGroupRequest;
import com.gjorovski.auctioneer.auth.request.UpdateGroupRequest;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.request.CreateGroupRequest;
import com.gjorovski.auctioneer.auth.response.GroupResponse;
import com.gjorovski.auctioneer.shared.exceptions.PermissionDeniedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth/groups")
public class GroupController {
    private final GroupService groupService;
    private final ModelMapper modelMapper;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups(@RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        List<Group> groups = groupService.getGroups();
        List<GroupResponse> groupResponses = groups.stream()
                .map(group -> modelMapper.map(group, GroupResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(groupResponses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable long id, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = groupService.getGroupById(id);
        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = modelMapper.map(createGroupRequest, Group.class);
        Group createdGroup = groupService.createGroup(group);
        GroupResponse groupResponse = modelMapper.map(createdGroup, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable long id, @RequestBody @Valid UpdateGroupRequest updateGroupRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = modelMapper.map(updateGroupRequest, Group.class);
        Group updatedGroup = groupService.updateGroup(id, group);
        GroupResponse groupResponse = modelMapper.map(updatedGroup, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GroupResponse> deleteGroup(@PathVariable long id, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = groupService.deleteGroup(id);
        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PostMapping("add_user_to_group")
    public ResponseEntity<GroupResponse> addUserToGroup(@RequestBody AddUserToGroupRequest addUserToGroupRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = groupService.addUserToGroup(addUserToGroupRequest.getUserId(), addUserToGroupRequest.getGroupId());
        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PostMapping("remove_user_from_group")
    public ResponseEntity<GroupResponse> removeUserFromGroup(@RequestBody RemoveUserFromGroupRequest removeUserFromGroupRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup(GroupType.ADMIN)) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = groupService.removeUserFromGroup(removeUserFromGroupRequest.getUserId(), removeUserFromGroupRequest.getGroupId());
        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }
}
