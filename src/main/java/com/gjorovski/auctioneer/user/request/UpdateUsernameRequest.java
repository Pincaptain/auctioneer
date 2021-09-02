package com.gjorovski.auctioneer.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUsernameRequest {
    @NotNull(message = "Username cannot be null.")
    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 6, max = 64, message = "Username must be between 6 and 64 characters.")
    private String username;
}
