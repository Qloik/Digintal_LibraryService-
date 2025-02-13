package com.library.new_library.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.User;
import com.library.new_library.mapper.UserMapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public interface UserService extends IService<User> {

    @Autowired
    UserMapper userMapper = null;
    
    public Map<String,String> login(Object userid, Object password);

    int register(Object userid, Object nickname, Object usermail, Object password);

    List<User> myInf(Object userid);

    boolean changepassword(Object userid, Object password, Object passwordupdated);

    boolean blockUser(String userid);

    boolean unBlockUser(String userid);

    List<User> getBlockedUsers();
}
