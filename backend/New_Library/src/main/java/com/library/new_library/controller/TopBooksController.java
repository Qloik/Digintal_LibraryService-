package com.library.new_library.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.entity.BookInfo;
import com.library.new_library.service.TopBooksService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/top-books")
public class TopBooksController {
	@Autowired
	private TopBooksService topBooksService;
	
//	 获取借阅次数排前3名的书籍信息
	@GetMapping("/get")
	public List<BookInfo> get(){
		return topBooksService.getTopInfos();
	}
}
