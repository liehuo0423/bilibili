package com.bilibili.controller.aspect;

import com.bilibili.controller.support.UserSupport;
import com.bilibili.domain.UserMoment;
import com.bilibili.domain.auth.AuthRoleMenu;
import com.bilibili.domain.auth.UserRole;
import com.bilibili.domain.constant.AuthRoleConstant;
import com.bilibili.domain.exception.ConditionException;
import com.bilibili.service.AuthRoleMenuService;
import com.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
public class DataLimitedAspect {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(com.bilibili.domain.annotation.DataLimited)")
    public void check() {
    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> userRoleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof UserMoment){
                UserMoment userMoment = (UserMoment)arg;
                if(userRoleCodeSet.contains(AuthRoleConstant.ROLE_LV1) && !"0".equals(userMoment.getType())){
                    throw new ConditionException("参数错误");
                }
            }
        }
    }


}
