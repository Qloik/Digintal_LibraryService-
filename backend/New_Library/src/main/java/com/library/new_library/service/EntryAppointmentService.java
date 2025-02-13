package com.library.new_library.service;

import com.library.new_library.entity.EntryAppointment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface EntryAppointmentService extends IService<EntryAppointment> {

    public boolean reserve(Object userid);

    public boolean cancel(Object userid);
}
