package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.config.LoginInterceptor;
import com.library.new_library.entity.User;
import com.library.new_library.mapper.UserMapper;
import com.library.new_library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Map<String,String> login(Object userid, Object password) {

		Map<String,String> result = new HashMap<>();

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid);
		List<User> users = userMapper.selectList(queryWrapper);
		if (users.size() == 0)
		{
			result.put("information","unregistered");
			return result;
		}
		queryWrapper.eq("password", password);
		List<User> users2 = userMapper.selectList(queryWrapper);
		if (users2.size() == 0)
		{
			result.put("information","wrong password");
			return result;
		}

		queryWrapper.like("if_administrator","-");
		List<User> userBlock = userMapper.selectList(queryWrapper);
		if(userBlock.size()>0)
		{
			result.put("information", "account blocked");
			return result;
		}
		queryWrapper.clear();

		queryWrapper.eq("user_id", userid).eq("password", password).eq("if_administrator", "2");
		List<User> users3 = userMapper.selectList(queryWrapper);

		if(users3.size() == 0) {
			result.put("information", "success");
		}
		else
		{
			result.put("information","administrator");
		}
		result.put("token",LoginInterceptor.GenerateToken((String)userid));
		return result;
	}

	@Override
	public int register(Object userid, Object nickname, Object usermail, Object password) {
		User user = new User();
		user.setUserId((String) userid);
		user.setNickName((String) nickname);
		user.setUsermail((String) usermail);
		user.setPassword((String) password);
		user.setIfAdministrator("0");
		user.setUsername((String) nickname);

		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid);
		List<User> users = userMapper.selectList(queryWrapper);
		if (users.size() != 0) {
			return 0;// 该账号已被注册
		}

		int insert = baseMapper.insert(user);

		if (insert >= 1)
			return 1;// 注册成功
		else
			return 0;
	}

	@Override
	public List<User> myInf(Object userid) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid.toString());

		List<User> user = userMapper.selectList(queryWrapper);

		return user;
	}

	@Override
	public boolean changepassword(Object userid, Object password, Object passwordupdated) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid.toString());

		User user = userMapper.selectOne(queryWrapper);

		System.out.println(user.getPassword());

		if (!Objects.equals(user.getPassword(), password.toString())) {
			return false; // 密码错误
		}

		user.setPassword((String) passwordupdated);

		UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.eq("user_id", userid.toString());
		int update = baseMapper.update(user, userUpdateWrapper);

		if (update >= 1)
			return true;
		else
			return false;

	}
    @Override
	public boolean blockUser(String userid)
	{
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid);
		User user = userMapper.selectOne(queryWrapper);
		if(user.getIfAdministrator().length()>1){
			//该用户已经在黑名单内，不必再次加入
			return false;
		}
		user.setIfAdministrator("-"+user.getIfAdministrator());
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("userid",userid);
		int result = userMapper.updateById(user);
		if(result >=1)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	@Override
	public boolean unBlockUser(String userid)
	{
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid);
		User user = userMapper.selectOne(queryWrapper);

		if(user.getIfAdministrator().charAt(0) == '-')
		{
			user.setIfAdministrator(String.valueOf(user.getIfAdministrator().charAt(1)));
			UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("userid",userid);
			int result = userMapper.updateById(user);
			if(result >=1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<User> getBlockedUsers()
	{
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("if_administrator","-");
		List<User> users = userMapper.selectList(queryWrapper);
		return users;
	}
}
