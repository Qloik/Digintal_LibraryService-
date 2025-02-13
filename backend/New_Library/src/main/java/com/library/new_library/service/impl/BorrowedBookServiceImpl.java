package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.*;
import com.library.new_library.mapper.*;
import com.library.new_library.service.BorrowedBookService;
import com.library.new_library.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class BorrowedBookServiceImpl extends ServiceImpl<BorrowedBookMapper, BorrowedBook> implements BorrowedBookService {
    @Autowired
    private BorrowedBookMapper borrowedBookMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ForborrowedBookBookinfoMapper forborrowedBookBookinfoMapper;

    @Autowired
    private TopBooksMapper topBooksMapper;

    @Autowired
    private ReserveMapper reserveMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    private final ExecutorService executor = Executors.newCachedThreadPool() ;


    @Override
    public List<BorrowedBook> borrowedbyUserid(Object userid) {
        QueryWrapper<BorrowedBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid.toString());

        return borrowedBookMapper.selectList(queryWrapper);
    }

    @Override
    public List<BorrowedBook> getUnreceived() {
        return borrowedBookMapper.getUnreceived();
    }

    @Override
    public List<BorrowedBook> getReceived() {
        return borrowedBookMapper.getReceived();
    }

    @Override
    public boolean postponeborrowed(Object bookid, Object returnTime) {
        BorrowedBook borrowedBook = new BorrowedBook();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(returnTime.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            borrowedBook.setReturnTime(dateFormat.format(cal.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        UpdateWrapper<BorrowedBook> borrowedBookUpdateWrapper = new UpdateWrapper<>();
        borrowedBookUpdateWrapper.eq("book_id", bookid.toString());

        int update = baseMapper.update(borrowedBook, borrowedBookUpdateWrapper);

        return update >= 1;

    }

    @Override
    @Async
    public void returnborrowed(Object borrowId, String status) {
        BorrowedBook borrowedBook = new BorrowedBook();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        borrowedBook.setBackTime(dateFormat.format(date));

        borrowedBook.setBorrowStatus(status);
        //更新这一条借阅记录
        UpdateWrapper<BorrowedBook> borrowedBookUpdateWrapper = new UpdateWrapper<>();
        borrowedBookUpdateWrapper.eq("borrow_id", borrowId.toString());
        baseMapper.update(borrowedBook, borrowedBookUpdateWrapper);

        //更新这本书的借阅状态
        BorrowedBook borrowedBook1=borrowedBookMapper.selectOne(borrowedBookUpdateWrapper);
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
        bookUpdateWrapper.eq("book_id", borrowedBook1.getBookId());
        Book book=bookMapper.selectOne(bookUpdateWrapper);
        book.setBorrowState("未借阅");

        bookMapper.update(book,bookUpdateWrapper);

        executor.submit(() -> {

            try {
                //检查书籍是否被预留，并逐一发送邮件
                QueryWrapper< Reserve> queryWrapper=new QueryWrapper<>();
                String place=book.getPlace();
                String name=borrowedBook1.getBookName();
                queryWrapper.eq("isbn",borrowedBook1.getIsbn());
                queryWrapper.eq("place",place);
                List<Reserve> reserveList=reserveMapper.selectList(queryWrapper);
                if(reserveList.size()>0){ //有人预留此书
                    for(Reserve reserve:reserveList){
                        String userId=reserve.getUserId();
                        String to=userMapper.selectById(userId).getUsermail();
                        String subject="图书馆预约通知";
                        String body ="尊敬的用户，您好。根据您此前的预约申请服务，您预约的书籍《"+name+"》在 "
                                +place+" 图书馆内有余本，请尽快到馆办理借书业务。";
                        emailService.sendEmail(to,subject,body); //发邮件提醒
                    }
                }
                //删除这些预留记录
                reserveMapper.delete(queryWrapper);

            }catch(Exception e) {

                throw new RuntimeException("预留邮件发送失败");

            }

        });
    }

    @Override
    public String countborrowed(Object userid) {
        QueryWrapper<BorrowedBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid.toString());

        return borrowedBookMapper.selectCount(queryWrapper).toString();

    }

    //生成10位随机数
    private String random10(){
        Random random=new Random();
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder();
        int length = 10;
        for(int i = 0; i< length; i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 借阅记录为“已取书”，条目有效期一个月
     * @param borrowId: 借阅id
     * @return boolean
     */
    @Override
    public boolean changeStatus(String borrowId){
        BorrowedBook borrowedBook=borrowedBookMapper.selectById(borrowId);
        borrowedBook.setBorrowStatus("已取书");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        borrowedBook.setBorrowTime(dateFormat.format(date));

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        borrowedBook.setReturnTime(dateFormat.format(cal.getTime()));

        return borrowedBookMapper.updateById(borrowedBook)>0;
    }

    /**
     * 借阅记录为“未取书”，条目有效期三天
     * @param isbn:
     * @param userid: 借书的用户
     * @param place: 书籍所属校区
     * @return boolean 借阅是否成功
     */
    @Override
    public boolean borrow(Object isbn, Object userid,Object place) {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setIsbn((String) isbn);
        borrowedBook.setUserId((String) userid);
        borrowedBook.setBorrowStatus("未取书");

        QueryWrapper<ForborrowedBookBookinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn", isbn.toString());
        queryWrapper.eq("borrow_state", "未借阅");
        queryWrapper.eq("place", place.toString());
        List<ForborrowedBookBookinfo> forborrowedBookBookinfo = forborrowedBookBookinfoMapper.selectList(queryWrapper); //找到借出的信息
        borrowedBook.setBookName(forborrowedBookBookinfo.get(0).getBookName());
        borrowedBook.setBookId(forborrowedBookBookinfo.get(0).getBookId());

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        borrowedBook.setBorrowTime(dateFormat.format(date));

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        borrowedBook.setReturnTime(dateFormat.format(cal.getTime()));

        borrowedBook.setBorrowId(forborrowedBookBookinfo.get(0).getBookId()+random10());

        int insert = borrowedBookMapper.insert(borrowedBook);

        QueryWrapper<Book> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("book_id", forborrowedBookBookinfo.get(0).getBookId());
        Book book = bookMapper.selectOne(queryWrapper2);
        book.setBorrowState("已借出");
        bookMapper.update(book, queryWrapper2);

        QueryWrapper<TopBooks> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("isbn", isbn.toString());
        TopBooks topBooks2 = topBooksMapper.selectOne(queryWrapper3);
        topBooks2.setBorrowedTimes(topBooks2.getBorrowedTimes() + 1);
        topBooksMapper.update(topBooks2, queryWrapper3);

        return insert >= 1;
    }

    @Override
    public int countisbnplace(Object isbn, Object place){
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn.toString());
        queryWrapper.eq("place",place.toString());
        queryWrapper.eq("borrow_state","未借阅");

        List<Book> books = bookMapper.selectList(queryWrapper);
        return books.size();
    }
}