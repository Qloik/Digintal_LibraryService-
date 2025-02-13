package com.library.new_library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.RoomAppointment;
import com.library.new_library.service.RoomAppointmentService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/room-appointment")
public class RoomAppointmentController {

	@Autowired
	private RoomAppointmentService roomAppointmentService;

	@PostMapping("/GetRoom")
	public List<RoomAppointment> GetRoom(@RequestParam Map<String, Object> param) {
		return roomAppointmentService.GetRoomByTypeAndTime(param.get("date"), param.get("type"));
	}
	
	@UserLoginToken
	@PostMapping("/AppointById")
	public boolean AppointById(@RequestParam Map<String, Object> param) {
		return roomAppointmentService.MakeAppointById(param.get("userid"), param.get("reserve_id"));
	}

	@UserLoginToken
	@PostMapping("/GetAppointByUser")
	public List<RoomAppointment> GetAppointByUser(@RequestParam Map<String, Object> param) {
		return roomAppointmentService.GetAppointByUser(param.get("userid"));
	}

	@UserLoginToken
	@PostMapping("/CancelAppoint")
	public boolean CancelAppoint(@RequestParam Map<String, Object> param) {
		return roomAppointmentService.CancelAppoint(param.get("reserve_id"));
	}
	
	@UserLoginToken
	@PostMapping("/theroomnum")
	public String theroomnum(@RequestParam Map<String, Object> param) {
		return roomAppointmentService.theroomnum(param.get("userid"));
	}

}
