package com.library.new_library.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.Book;
import com.library.new_library.entity.BookCount;
import com.library.new_library.mapper.BookCountMapper;
import com.library.new_library.service.BookInfoService;
import com.library.new_library.service.BookService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private BookCountMapper bookCountMapper;
	
	// 将数字转化为2位字符串
	private String convert(int num) {
		if(num < 9) {
			return "0" + String.valueOf(num);
		}
		else {
			return String.valueOf(num);
		}
	}
	
	@UserLoginToken
	@PostMapping("/save")
	public boolean addBooks(@RequestParam String isbn,
			@RequestParam(value = "countJiading",defaultValue = "0",required = false)int countJiading,
			@RequestParam(value = "countSiping",defaultValue = "0",required = false)int countSiping) {
		// 不存在BookInfo时，不允许新增书籍
		if(bookInfoService.queryBookInfo(isbn)==null) {
			return false;
		}
		BookCount bookCount=bookCountMapper.findByIsbn(isbn);
		int num = bookCount.getCount();
		for(int i=1;i<=countJiading;i++) {
			Book param = new Book();
			String id = isbn + convert(num+i);
			param.setBookId(id);
			param.setBorrowState("未借阅");
			param.setIsbn(isbn);
			param.setPlace("嘉定校区");
			if(!bookService.save(param))
				return false;
			
		}
		for(int i=1;i<=countSiping;i++) {
			Book param = new Book();
			String id = isbn + convert(num+countJiading+i);
			param.setBookId(id);
			param.setBorrowState("未借阅");
			param.setIsbn(isbn);
			param.setPlace("四平路校区");
			if(!bookService.save(param)) {
				return false;
			}
		}
		return bookCountMapper.updateByIsbn(isbn, countJiading+countSiping);
	}
	
	@UserLoginToken
	@PostMapping("/modify")
	public boolean modify(@RequestBody Book param) {
		return bookService.updateById(param);
	}
	
	@UserLoginToken
	@PostMapping("/delete")
	public boolean delete(@RequestParam String bookId) {
		LambdaQueryWrapper<Book> lambdaQueryWrapper = new LambdaQueryWrapper<Book>();
		lambdaQueryWrapper.eq(Book::getBookId, bookId);
		return bookService.remove(lambdaQueryWrapper);
	}

	@PostMapping("/Count")
	public int Count(@RequestParam Map<String,Object> param){
		return bookService.Count(param.get("isbn"),param.get("place"));
	}



}
