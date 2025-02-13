package com.library.new_library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.BookInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface BookInfoService extends IService<BookInfo> {
	public BookInfo queryBookInfo(String isbn);
	
    public boolean updateScore(Object isbn, Object rate, Object count);
}
