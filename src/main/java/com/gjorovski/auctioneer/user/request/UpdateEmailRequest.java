package com.gjorovski.auctioneer.user.request;

import com.gjorovski.auctioneer.user.validation.UpdatableEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateEmailRequest {
    @NotNull(message = "Email cannot be null.")
    @NotBlank(message = "Email cannot be blank.")
    @Email
    @UpdatableEmail
    private String email;
}
