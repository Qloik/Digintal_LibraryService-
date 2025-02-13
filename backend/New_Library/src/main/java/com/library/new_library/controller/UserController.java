package com.library.new_library.controller;

import java.awt.*;
import java.util.List;
import java.util.Map;

import com.library.new_library.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.User;
import com.library.new_library.service.UserService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;

	@GetMapping
	public String test() {
		return "JUST FOR TEST";
	}

	@GetMapping("/list")
	public List<User> list() {
		return userService.list();
	}

	@PostMapping("/login")
	public Map<String, String> login(@RequestParam Map<String, Object> param) {
		return userService.login(param.get("userid"), param.get("password"));
	}

	@PostMapping("/register")
	public int register(@RequestParam Map<String, Object> param) {
		return userService.register(param.get("userid"), param.get("nickname"), param.get("usermail"),
				param.get("password"));
	}

	@UserLoginToken
	@PostMapping("/myInf")
	public List<User> myInf(@RequestParam Map<String, Object> param) {
		return userService.myInf(param.get("userid"));
	}

	@UserLoginToken
	@PostMapping("/changepassword")
	public boolean changepassword(@RequestParam Map<String, Object> param) {
		return userService.changepassword(param.get("userid"), param.get("password"), param.get("passwordupdated"));
	}


	@PostMapping("/blockuser")
	public boolean blockUser(String userid)
	{
		return userService.blockUser(userid);
	}


	@PostMapping("/unblockuser")
	public boolean unblockUser(String userid)
	{
		return userService.unBlockUser(userid);
	}


	@GetMapping("/getBlockedUsers")
	public List<User> getBlockedUsers()
	{
		return userService.getBlockedUsers();
	}

	@PostMapping("/sendEmail")
	public boolean sendEmail(@RequestParam String to)
	{
		String subject="图书馆预约通知";
		String body ="尊敬的用户，您好。根据您此前的预约申请服务，您预约的书籍已经在图书馆内有余本，请尽快到馆办理借书业务";
		return emailService.sendEmail(to,subject,body);
	}
}
