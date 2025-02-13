package com.library.new_library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.Reserve;
import com.library.new_library.mapper.UserMapper;
import com.library.new_library.service.BookInfoService;
import com.library.new_library.service.EmailService;
import com.library.new_library.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2023-05-31
 */
@RestController
@RequestMapping("/reserve")
public class ReserveController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private BookInfoService bookInfoService;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Async
    @PostMapping("/saveReserve")
    public void saveReserve(@RequestParam Map<String,Object> param){
        String isbn=param.get("isbn").toString();
        String userId=param.get("userId").toString();
        String place=param.get("place").toString();
        //如果已经预约了
        QueryWrapper<Reserve> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn);
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("place",place);
        List<Object> tmpList = reserveService.listObjs(queryWrapper);
        if(tmpList.size()>0){
            System.out.println("您已预约过这本书，请不要重复预约。");
            return;
        }
        Reserve reserve=new Reserve();
        reserve.setIsbn(isbn);
        reserve.setUserId(userId);
        reserve.setPlace(place);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        reserve.setRetime(dateFormat.format(date));
        boolean result= reserveService.save(reserve);
        if(result){
            //异步发送邮件
            executor.submit(() -> {

                try {
                    String to = userMapper.selectById(userId).getUsermail();
                    String subject="图书馆预约通知";
                    BookInfo bookInfo=bookInfoService.queryBookInfo(isbn);
                    String name=bookInfo.getBookName();
                    String body="尊敬的用户，您好。您已预约《"+name+"》，当该书籍在 "+place+" 图书馆内有余本时，我们将发邮件提醒您。";
                    emailService.sendEmail(to,subject,body);
                }
                catch (Exception e) {

                    throw new RuntimeException("预留邮件发送失败");

                }

            });

            System.out.println("预约成功");
            return;
        }
        System.out.println("预约失败");
    }

    @PostMapping("/deleteReserve")
    public boolean deleteReserve(@RequestParam Map<String,Object> param){
        QueryWrapper<Reserve> queryWrapper=new QueryWrapper<>();
        String isbn=param.get("isbn").toString();
        String userId=param.get("userId").toString();
        String place=param.get("place").toString();
        queryWrapper.eq("isbn",isbn);
        queryWrapper.eq("place",place);
        queryWrapper.eq("user_id",userId);
        return reserveService.remove(queryWrapper);
    }

    @PostMapping("/getByUserId")
    public List<Reserve> getByUserId(@RequestParam Map<String,Object> param){
        String userId=param.get("userId").toString();
        QueryWrapper<Reserve> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Reserve> reserveList=reserveService.list(queryWrapper);
        //将用户id更改为书名
        for(Reserve reserve:reserveList){
            String name=bookInfoService.queryBookInfo(reserve.getIsbn()).getBookName();
            reserve.setUserId(name);
        }
        return reserveList;
    }
}
