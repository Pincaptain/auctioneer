package com.gjorovski.auctioneer.auth.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddUserToGroupRequest {
    @NotNull(message = "User ID cannot be null.")
    @NotBlank(message = "User ID cannot be blank.")
    private long userId;

    @NotNull(message = "Group ID cannot be null.")
    @NotBlank(message = "Group ID cannot be blank.")
    private long groupId;
}
