package com.library.new_library.service;

import com.library.new_library.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface NoticeService extends IService<Notice> {

    public boolean EditNotice(Object id, Object title, Object text, Object userid);

    public boolean AddNotice(Object title, Object text, Object userid);

    public boolean DeleteNotice(Object id);
}
