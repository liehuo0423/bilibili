package com.bilibili.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
@Mapper
public interface UserCoinMapper {
    Integer getUserCoinsAmount(Long userId);

    Integer updateUserCoinAmount(@Param("userId") Long userId,
                                 @Param("amount") Integer amount,
                                 @Param("updateTime") Date updateTime);
}
