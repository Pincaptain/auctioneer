package com.gjorovski.auctioneer.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordRequest {
    @NotNull(message = "Password cannot be null.")
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @NotNull(message = "New password cannot be null.")
    @NotBlank(message = "New password cannot be blank.")
    @Size(min = 8, message = "New password must be at least 8 characters long.")
    private String newPassword;
}
