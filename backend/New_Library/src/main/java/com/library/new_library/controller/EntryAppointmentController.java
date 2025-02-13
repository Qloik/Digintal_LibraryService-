package com.library.new_library.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.service.EntryAppointmentService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/entry-appointment")
public class EntryAppointmentController {

    @Autowired
    private EntryAppointmentService entryAppointmentService;
    
    @UserLoginToken
    @PostMapping("/reserve")
    public boolean reserve(@RequestParam Map<String,Object> param)
    {
        return entryAppointmentService.reserve(param.get("userid"));
    }
    
    @UserLoginToken
    @PostMapping("/cancel")
    public boolean cancel(@RequestParam Map<String,Object> param)
    {
        return entryAppointmentService.cancel(param.get("userid"));
    }
    
    @GetMapping("/getNumber")
    public int getNumber()
    {
        return entryAppointmentService.count();
    }
}
