package com.library.new_library.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.BookCount;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.TopBooks;
import com.library.new_library.mapper.BookCountMapper;
import com.library.new_library.mapper.TopBooksMapper;
import com.library.new_library.service.BookInfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/book-info")
public class BookInfoController {
	@Autowired
	private BookInfoService bookInfoService;
	@Autowired
	private BookCountMapper bookCountMapper;
	@Autowired
	private TopBooksMapper topBooksMapper;
	
	@UserLoginToken
	@PostMapping("/saveOrMod")
	public boolean saveOrmod(@RequestBody BookInfo params) {
		Date date = new Date(); // 获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd"); // 只记录年月日
		params.setUpdateTime(df.format(date));
		// 如果是新增
		String isbn = params.getIsbn();
		if(queryByIsbn(isbn)==null) {
			// isbn符合基本规范
			if(isbn.matches("^[0-9]{13}$")){
				params.setScore(0.0);
				params.setScorePeopleNumber(0);
				if(bookInfoService.save(params)) {
					// 增加BookCount记录
					BookCount bookCount = new BookCount();
					bookCount.setCount(0);
					bookCount.setIsbn(isbn);
					bookCountMapper.insert(bookCount);
					
					// 增加TopBooks记录
					TopBooks topBooks = new TopBooks();
					topBooks.setIsbn(isbn);
					if(params.getBookName()!=null)
						topBooks.setBookName(params.getBookName());
					topBooks.setBorrowedTimes(0);
					topBooksMapper.insert(topBooks);
					return true;
				}
				else
					return false;
			}
			else {
				return false;
			}
		}
		else {
			LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(BookInfo::getIsbn, isbn);
			return bookInfoService.update(params, lambdaQueryWrapper);
		}
	}
	
	// 自动判断输入的信息，获取相关书籍
	@PostMapping("/query")
	public List<BookInfo> query(String content){
		// 由13位数字组成，是isbn
		if(content.matches("^[0-9]{13}$")){
			LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(BookInfo::getIsbn, content);
			return bookInfoService.list(lambdaQueryWrapper);
		}
		// 可能是书名或人名
		else {
			LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.like(BookInfo::getBookName, content).or().eq(BookInfo::getBookAuthor, content);
			return bookInfoService.list(lambdaQueryWrapper);
		}
	}
	
	// 根据isbn，获取书籍信息
	@PostMapping("/queryByIsbn")
	public BookInfo queryByIsbn(@RequestParam String isbn){
		// 想尝试一下，不行就改回原来的
		return bookInfoService.queryBookInfo(isbn);
	}
	
	// 获取全部的书籍信息
	@UserLoginToken
	@GetMapping("/get")
	public List<BookInfo> get(){
		return bookInfoService.list();
	}
	
	// 根据isbn，删除书籍信息
	@UserLoginToken
	@PostMapping("/delete")
	public boolean delete(@RequestParam String isbn) {
		LambdaQueryWrapper<BookInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(BookInfo::getIsbn, isbn);
		return bookInfoService.remove(lambdaQueryWrapper);
	}
	
	@UserLoginToken
	@PostMapping("/updateScore")
	public boolean updateScore(@RequestParam Map<String,Object> param)
	{
		return bookInfoService.updateScore(param.get("isbn"),param.get("rate"),param.get("count"));
	}

}
