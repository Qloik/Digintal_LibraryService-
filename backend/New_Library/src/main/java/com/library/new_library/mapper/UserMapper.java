package com.library.new_library.mapper;

import org.mapstruct.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.User;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {


}
