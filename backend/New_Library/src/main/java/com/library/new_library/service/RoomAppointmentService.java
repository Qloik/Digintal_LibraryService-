package com.library.new_library.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.RoomAppointment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface RoomAppointmentService extends IService<RoomAppointment> {
    public List<RoomAppointment> GetRoomByTypeAndTime(Object date,Object type);

    public boolean MakeAppointById(Object userid, Object reserve_id);

    public List<RoomAppointment> GetAppointByUser(Object userid);

    public boolean CancelAppoint(Object reserve_id);

    String theroomnum(Object userid);
}
