package com.gjorovski.auctioneer.auth.validation;

import com.gjorovski.auctioneer.auth.model.Group;
import com.gjorovski.auctioneer.auth.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class UpdatableGroupNameValidation implements ConstraintValidator<UpdatableGroupName, String> {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private GroupService groupService;

    @Override
    public void initialize(UpdatableGroupName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long id = Long.parseLong(parameters.get("id"));
        Group group = groupService.getGroup(id);

        if (group.getName().equals(name)) {
            return true;
        }

        Group other = groupService.getGroupByName(name);

        return other == null;
    }
}
