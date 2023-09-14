package com.bilibili.controller.aspect;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.annotation.ApiLimitedRole;
import com.bilibili.domain.auth.UserRole;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Aspect
@Order(1)
@Component
public class ApiLimitedRoleAspect {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(com.bilibili.domain.annotation.ApiLimitedRole)")
    public void check() {
    }

    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> userRoleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        userRoleCodeSet.retainAll(limitedRoleCodeSet);
        if (userRoleCodeSet.size() > 0) {
            throw new ConditionException("权限不足");
        }
    }
}
