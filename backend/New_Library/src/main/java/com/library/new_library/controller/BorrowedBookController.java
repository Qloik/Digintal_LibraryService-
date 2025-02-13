package com.library.new_library.controller;


import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.BorrowedBook;
import com.library.new_library.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/borrowed-book")
public class BorrowedBookController {
    @Autowired
    BorrowedBookService borrowedBookService;
    
    @UserLoginToken
    @PostMapping("/getborrowedbyUserid")
    public List<BorrowedBook> borrowedbyUserid(@RequestParam Map<String, Object>param){
        return borrowedBookService.borrowedbyUserid(param.get("userid"));
    }
    
    @UserLoginToken
    @PostMapping("/postponeborrowed")
    public boolean postponeborrowed(@RequestParam Map<String,Object> param){
        return borrowedBookService.postponeborrowed(param.get("bookid"),param.get("returnTime"));
    }

    @PostMapping("/returnborrowed")
    public void returnborrowed(@RequestParam Map<String,Object> param) {
        borrowedBookService.returnborrowed(param.get("borrowId"), "已归还");
    }

    //统计user借阅的图书总数
    @UserLoginToken
    @PostMapping("/countborrowed")
    public String countborrowed(@RequestParam Map<String,Object> param){
        return borrowedBookService.countborrowed(param.get("userid"));
    }

    @PostMapping("/countisbnplace")
    public int countisbnplace(@RequestParam Map <String,Object> param){
        return borrowedBookService.countisbnplace(param.get("isbn"),param.get("place"));
    }

    @UserLoginToken
    @PostMapping("/borrow")
    public boolean borrow(@RequestParam Map<String,Object> param){
        return borrowedBookService.borrow(param.get("isbn"),param.get("userid"),param.get("place"));
    }

    @UserLoginToken
    @PostMapping("/receiveBook")
    public boolean receiveBook(@RequestParam String borrowId){
        return borrowedBookService.changeStatus(borrowId);
    }

    @GetMapping("/getUnreceived")
    public List<BorrowedBook> getUnreceived(){
        return borrowedBookService.getUnreceived();
    }

    @GetMapping("/getReceived")
    public List<BorrowedBook> getReceived(){
        //包括已取书、已过期
        return borrowedBookService.getReceived();
    }

}
