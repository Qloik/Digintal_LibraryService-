package com.library.new_library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.entity.BookCountCampus;
import com.library.new_library.service.BookCountCampusService;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/book-count-campus")
public class BookCountCampusController {

	@Autowired
	private BookCountCampusService bookCountCampusService;

	@GetMapping("/get")
	public List<BookCountCampus> list() {
		return bookCountCampusService.list();
	}
}
