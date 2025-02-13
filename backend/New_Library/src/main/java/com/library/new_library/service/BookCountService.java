package com.library.new_library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.BookCount;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
public interface BookCountService extends IService<BookCount> {
	public BookCount queryByIsbn(String isbn);
}
