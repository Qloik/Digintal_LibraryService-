package com.library.new_library.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.entity.BookCount;
import com.library.new_library.service.BookCountService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
@RestController
@RequestMapping("/book-count")
public class BookCountController {
	@Autowired
	private BookCountService bookCountService;
	
	@PostMapping("/queryByIsbn")
	public BookCount queryByIsbn(@RequestParam String isbn) {
		return bookCountService.queryByIsbn(isbn);
	}
}
