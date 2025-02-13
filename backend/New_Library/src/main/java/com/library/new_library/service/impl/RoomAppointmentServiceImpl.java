package com.library.new_library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.RoomAppointment;
import com.library.new_library.mapper.RoomAppointmentMapper;
import com.library.new_library.service.RoomAppointmentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class RoomAppointmentServiceImpl extends ServiceImpl<RoomAppointmentMapper, RoomAppointment> implements RoomAppointmentService {

    @Autowired
    private RoomAppointmentMapper roomAppointmentMapper;
    @Override
    public List<RoomAppointment> GetRoomByTypeAndTime(Object date,Object type) {
//        Example example = new Example(RoomAppointment.class);//指定查询的表
//        Example.Criteria criteria = example.createCriteria();//创建查询条件
//
//        if(searchMap!=null){
//            criteria.andEqualTo("reserveDate",searchMap.get("date"));
//            criteria.andEqualTo("roomType",searchMap.get("type"));
//        }
//
//        List<RoomAppointment> roomAppointments = roomAppointmentMapper.

        QueryWrapper<RoomAppointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reserve_date",date.toString());
        queryWrapper.eq("room_type",type.toString());

        List<RoomAppointment> roomAppointments = roomAppointmentMapper.selectList(queryWrapper);

        return roomAppointments;
    }

    @Override
    public boolean MakeAppointById(Object userid, Object reserve_id) {
        RoomAppointment roomAppointment = new RoomAppointment();
        roomAppointment.setUserId((String) userid);
        roomAppointment.setRoomStatus("已预约");

        UpdateWrapper<RoomAppointment> roomAppointmentUpdateWrapper = new UpdateWrapper<>();
        roomAppointmentUpdateWrapper.eq("reserve_id",reserve_id.toString());

        int update = baseMapper.update(roomAppointment,roomAppointmentUpdateWrapper);

        if (update>=1)
            return true;
        else
            return false;
    }

    @Override
    public List<RoomAppointment> GetAppointByUser(Object userid) {

        QueryWrapper<RoomAppointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userid.toString());
        List<RoomAppointment> roomAppointments = roomAppointmentMapper.selectList(queryWrapper);

        return roomAppointments;
    }

    @Override
    public boolean CancelAppoint(Object reserve_id) {
        RoomAppointment roomAppointment = new RoomAppointment();
        roomAppointment.setUserId(null);
        roomAppointment.setRoomStatus("未预约");

        UpdateWrapper<RoomAppointment> roomAppointmentUpdateWrapper = new UpdateWrapper<>();
        roomAppointmentUpdateWrapper.eq("reserve_id",reserve_id.toString());
        int update = baseMapper.update(roomAppointment,roomAppointmentUpdateWrapper);

        if (update>=1)
            return true;
        else
            return false;
    }

    @Override
    public String theroomnum(Object userid){
        QueryWrapper<RoomAppointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userid.toString());

        String roomappointnum= roomAppointmentMapper.selectCount(queryWrapper).toString();

        return roomappointnum;

    }
}
