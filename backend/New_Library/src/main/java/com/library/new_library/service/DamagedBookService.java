package com.library.new_library.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.DamagedBook;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface DamagedBookService extends IService<DamagedBook> {

    List<DamagedBook> damagedbyUserid(Object userid);



    boolean adddamagedbook(Object bookId, Object userId);

    boolean repairdamagedbook(Object bookId, Object damageTime);

    List<DamagedBook> getdamaged();
}
