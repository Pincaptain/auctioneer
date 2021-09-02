package com.gjorovski.auctioneer.auth.controller;

import com.gjorovski.auctioneer.auth.data.Authentication;
import com.gjorovski.auctioneer.auth.service.GroupService;
import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.request.GroupRequest;
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
        if (!authentication.inGroup("Admin")) {
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
        if (!authentication.inGroup("Admin")) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = groupService.getGroup(id);
        GroupResponse groupResponse = modelMapper.map(group, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid GroupRequest groupRequest, @RequestAttribute Authentication authentication) {
        if (!authentication.inGroup("Admin")) {
            throw new PermissionDeniedException(Authentication.NOT_AUTHENTICATED_MESSAGE);
        }

        Group group = modelMapper.map(groupRequest, Group.class);
        Group createdGroup = groupService.createGroup(group);
        GroupResponse groupResponse = modelMapper.map(createdGroup, GroupResponse.class);

        return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
    }
}
