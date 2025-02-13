package com.library.new_library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.TopBooks;
import com.library.new_library.mapper.TopBooksMapper;
import com.library.new_library.service.TopBooksService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class TopBooksServiceImpl extends ServiceImpl<TopBooksMapper, TopBooks> implements TopBooksService {
	@Autowired
	private TopBooksMapper topBooksMapper;
	
	public List<BookInfo> getTopInfos(){
		return topBooksMapper.getTopInfos();
	}
}
