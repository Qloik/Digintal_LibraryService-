package com.library.new_library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.BookCount;
import com.library.new_library.mapper.BookCountMapper;
import com.library.new_library.service.BookCountService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
@Service
public class BookCountServiceImpl extends ServiceImpl<BookCountMapper, BookCount> implements BookCountService {
	@Autowired
	private BookCountMapper bookCountMapper;
	
	public BookCount queryByIsbn(String isbn) {
		return bookCountMapper.findByIsbn(isbn);
	}
}
