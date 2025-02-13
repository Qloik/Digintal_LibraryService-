package com.library.new_library.config;

import com.library.new_library.entity.BorrowedBook;
import com.library.new_library.service.BorrowedBookService;
import com.library.new_library.service.DamagedBookService;
import com.library.new_library.service.EmailService;
import com.library.new_library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author DQL
 * @time 2023/5/31
 */
@Component
public class BorrowUpdate implements ServletContextListener {
    @Autowired
    private BorrowedBookService borrowedBookService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private DamagedBookService damagedBookService;

    // 每天早上 3:00 触发；注意可能的情况：长期不用后再次启动，仍符合条件
    @Scheduled(cron = "0 55 14 ? * *")
    public void scheduled() throws ParseException {
        System.out.println("触发定时器");
        List<BorrowedBook> unreceived=borrowedBookService.getUnreceived();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime=dateFormat.format(date);
        Date currentDate=dateFormat.parse(currentTime);
        //当天为应失效时间
        for(BorrowedBook borrowedBook:unreceived){
            String time=borrowedBook.getBorrowTime();
            Date borrowDate=dateFormat.parse(time);

            long startTime = borrowDate.getTime();
            long endTime = currentDate.getTime();
            //计算间隔天数
            int days = (int) ((endTime - startTime) / (1000 * 60 * 60 * 24));
            if(days>1){
                borrowedBookService.returnborrowed(borrowedBook.getBorrowId(),"已失效");
                System.out.println("失效提醒："+borrowedBook.getBorrowId());
            }
            else if(days>0){
                String to=userService.getById(borrowedBook.getUserId()).getUsermail();
                String subject="图书馆借阅通知";
                String content="尊敬的用户，您好。您于 "+borrowedBook.getBorrowTime()+" 预借阅了《"
                        +borrowedBook.getBookName()+"》，请及时前往图书馆拿取。\n如果您当天仍未取走该书，那么这本书将不再为您保留。";
                emailService.sendEmail(to,subject,content);
                System.out.println("取书提醒："+borrowedBook.getBorrowId()+"，发给"+to);
            }
        }
        // 逾期未还，则将借阅条目状态置为“已过期”，将该书添加到损坏数目列表，并将该用户加入黑名单
        List<BorrowedBook> received=borrowedBookService.getReceived();
        for(BorrowedBook borrowedBook:received){
            String time=borrowedBook.getReturnTime();
            Date returnDate=dateFormat.parse(time);
            if(returnDate.compareTo(currentDate)<0){
                //借阅条目
                borrowedBook.setBorrowStatus("已过期");
                borrowedBookService.updateById(borrowedBook);
                String userId=borrowedBook.getUserId();
                //损坏
                damagedBookService.adddamagedbook(borrowedBook.getBookId(), userId);
                //黑名单
                userService.blockUser(userId);
                //邮件
                String to=userService.getById(userId).getUsermail();
                String subject="图书馆还书通知";
                String content="尊敬的用户，您好。您于 "+borrowedBook.getBorrowTime()+" 借阅了《"
                        +borrowedBook.getBookName()+"》，现已超过借阅期限。\n在归还该书之前，" +
                        "您将无法使用本系统的所有功能，请及时前往图书馆前台还书。若书籍不慎丢失，也可以在前台进行赔偿。";
                emailService.sendEmail(to,subject,content);
                System.out.println("过期提醒："+borrowedBook.getBorrowId()+"，发给"+to);
            }
        }
    }
}
