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
import com.library.new_library.entity.DamagedBook;
import com.library.new_library.service.DamagedBookService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/damaged-book")
public class DamagedBookController {

	@Autowired
	DamagedBookService damagedBookService;
	
	@UserLoginToken
	@PostMapping("/getdamagedbyUserid")
	public List<DamagedBook> damagedbyUserid(@RequestParam Map<String, Object> param) {
		return damagedBookService.damagedbyUserid(param.get("userid"));
	}

    @GetMapping("/getdamaged")
    public List<DamagedBook> getdamaged(){
        return damagedBookService.getdamaged();
    }
    
    @UserLoginToken
    @PostMapping("/adddamagedbook")
    public  boolean adddamagedbook(@RequestParam Map<String,Object> param) {
        return damagedBookService.adddamagedbook(param.get("bookId"),param.get("userId"));
    }

    @UserLoginToken
    @PostMapping("/repairdamagedbook")
    public  boolean repairdamagedbook(@RequestParam Map<String,Object> param) {
        return damagedBookService.repairdamagedbook(param.get("bookId"),param.get("damageTime"));
    }



}
