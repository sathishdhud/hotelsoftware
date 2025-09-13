package com.hotelmanagement.security;

import com.hotelmanagement.exception.BadRequestException;
import com.hotelmanagement.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserRightsAspect {

    @Autowired
    private UserService userService;

    @Before("@annotation(requiresPermission)")
    public void checkPermission(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal)) {
            throw new BadRequestException("User not authenticated");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUserId();
        String moduleName = requiresPermission.module();
        String permissionType = requiresPermission.permission();

        boolean hasPermission = false;
        
        switch (permissionType.toLowerCase()) {
            case "read":
                hasPermission = userService.canRead(userId, moduleName);
                break;
            case "write":
                hasPermission = userService.canWrite(userId, moduleName);
                break;
            default:
                hasPermission = userService.hasPermission(userId, moduleName, permissionType);
                break;
        }

        if (!hasPermission) {
            throw new BadRequestException("Access denied. Insufficient permissions for module: " + moduleName);
        }
    }
}
