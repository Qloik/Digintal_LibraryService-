package com.library.new_library.controller;


import com.library.new_library.entity.BookCountCampusAll;
import com.library.new_library.service.BookCountCampusAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * VIEW 前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-29
 */
@RestController
@RequestMapping("/book-count-campus-all")
public class BookCountCampusAllController {
    @Autowired
    private BookCountCampusAllService bookCountCampusAllService;

    @GetMapping("/get")
    public List<BookCountCampusAll> list() {
        return bookCountCampusAllService.list();
    }
}
