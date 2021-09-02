package com.gjorovski.auctioneer.auth.request;

import com.gjorovski.auctioneer.auth.validation.UniqueGroupName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateGroupRequest {
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 3, max = 16, message = "Name must be between 3 and 16 characters.")
    @UniqueGroupName
    private String name;
}
