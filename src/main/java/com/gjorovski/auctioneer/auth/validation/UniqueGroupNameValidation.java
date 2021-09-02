package com.gjorovski.auctioneer.auth.validation;

import com.gjorovski.auctioneer.auth.Group;
import com.gjorovski.auctioneer.auth.GroupService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueGroupNameValidation implements ConstraintValidator<UniqueGroupName, String> {
    private final GroupService groupService;

    public UniqueGroupNameValidation(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void initialize(UniqueGroupName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Group group = groupService.getGroupByName(name);

        return group == null;
    }
}
