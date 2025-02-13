package com.library.new_library.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.TopBooks;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface TopBooksService extends IService<TopBooks> {
	public List<BookInfo> getTopInfos();
}
