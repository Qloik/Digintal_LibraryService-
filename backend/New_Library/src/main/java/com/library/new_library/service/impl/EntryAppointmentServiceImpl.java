package com.library.new_library.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.EntryAppointment;
import com.library.new_library.mapper.EntryAppointmentMapper;
import com.library.new_library.service.EntryAppointmentService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class EntryAppointmentServiceImpl extends ServiceImpl<EntryAppointmentMapper, EntryAppointment>
		implements EntryAppointmentService {

	@Autowired
	private EntryAppointmentMapper entryAppointmentMapper;

	@Override
	public boolean reserve(Object userid) {
		QueryWrapper<EntryAppointment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid.toString());

		List<EntryAppointment> entryAppointments = entryAppointmentMapper.selectList(queryWrapper);

		if (entryAppointments.size() != 0)
			return false;// 已经预约过需要取消预约

		EntryAppointment entryAppointment = new EntryAppointment();
		entryAppointment.setUserId(userid.toString());
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		entryAppointment.setAppointTime(dateFormat.format(date));

		entryAppointmentMapper.insert(entryAppointment);
		return true;
	}

	@Override
	public boolean cancel(Object userid) {
		QueryWrapper<EntryAppointment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userid.toString());

		List<EntryAppointment> entryAppointments = entryAppointmentMapper.selectList(queryWrapper);

		if (entryAppointments.size() == 0)
			return false;// 没有预约过无法取消

		int result = entryAppointmentMapper.delete(queryWrapper);
		if(result>0) {
			return true;
		}
		else {
			return false;
		}
	}
}
