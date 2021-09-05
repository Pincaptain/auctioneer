package com.gjorovski.auctioneer.auth.validation;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class UpdatableGroupNameValidation implements ConstraintValidator<UpdatableGroupName, String> {
    private final HttpServletRequest httpServletRequest;
    private final GroupService groupService;

    public UpdatableGroupNameValidation(HttpServletRequest httpServletRequest, GroupService groupService) {
        this.httpServletRequest = httpServletRequest;
        this.groupService = groupService;
    }

    @Override
    public void initialize(UpdatableGroupName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long id = Long.parseLong(parameters.get("id"));
        Group group = groupService.getGroupById(id);

        if (group.getName().equals(name)) {
            return true;
        }

        Group other = groupService.getGroupByName(name);

        return other == null;
    }
}
