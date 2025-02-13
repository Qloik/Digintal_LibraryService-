package com.library.new_library.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.Notice;
import com.library.new_library.service.NoticeService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/GetNotice")
    public List<Notice> list(){
        return noticeService.list();
    }
    
    @UserLoginToken
    @PostMapping("/EditNotice")
    public boolean EditNotice(@RequestParam Map<String, Object> param){
        return noticeService.EditNotice(param.get("id"),param.get("title"),param.get("text"),param.get("userid"));
    }
    
    @UserLoginToken
    @PostMapping("/AddNotice")
    public boolean AddNotice(@RequestParam Map<String, Object> param){
        return noticeService.AddNotice(param.get("title"),param.get("text"),param.get("userid"));
    }

    @UserLoginToken
    @PostMapping("/DeleteNotice")
    public boolean DeleteNotice(@RequestParam Map<String, Object> param){
        return noticeService.DeleteNotice(param.get("id"));
    }
}
