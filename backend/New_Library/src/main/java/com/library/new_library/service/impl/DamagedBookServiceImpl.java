package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.Book;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.DamagedBook;
import com.library.new_library.mapper.BookInfoMapper;
import com.library.new_library.mapper.BookMapper;
import com.library.new_library.mapper.DamagedBookMapper;
import com.library.new_library.service.DamagedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class DamagedBookServiceImpl extends ServiceImpl<DamagedBookMapper, DamagedBook> implements DamagedBookService {

    @Autowired
    private DamagedBookMapper damagedBookMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;



    @Override
    public List<DamagedBook> damagedbyUserid(Object userid){
        QueryWrapper<DamagedBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userid.toString());


        return damagedBookMapper.selectList(queryWrapper);
    }


    @Override
    public  boolean adddamagedbook(Object bookId, Object userId){

        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id",bookId.toString());

        Book damage= this.bookMapper.selectOne(queryWrapper);
        if(damage==null)  //若book表中无该书籍
            return false;

        QueryWrapper<DamagedBook> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("book_id",bookId.toString());
        DamagedBook damagedBook=this.damagedBookMapper.selectOne(queryWrapper3);
        if(damagedBook!=null)//若damagebook表中已有该书籍
            return false;

        damagedBook=new DamagedBook();
        damagedBook.setBookId((String) bookId);
        damagedBook.setUserId((String) userId);
        damagedBook.setBorrowTime(damage.getPlace());
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        damagedBook.setDamageTime(dateFormat.format(date));

        QueryWrapper<BookInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("isbn",damage.getIsbn());
        String bookname=bookInfoMapper.selectOne(queryWrapper2).getBookName();
        damagedBook.setBookName(bookname);

        damagedBookMapper.insert(damagedBook);
        bookMapper.delete(queryWrapper);


        return true;

    }

    @Override
    public boolean repairdamagedbook(Object bookId, Object damageTime){
        String bookid=bookId.toString();
        QueryWrapper<DamagedBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id",bookid);

        DamagedBook damagedBook =  damagedBookMapper.selectOne(queryWrapper); //找到要更改的图书

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        damagedBook.setRepairTime(dateFormat.format(date));

        UpdateWrapper<DamagedBook> damagedBookUpdateWrapper = new UpdateWrapper<>();
        damagedBookUpdateWrapper.eq("book_id",bookid);
        int update = damagedBookMapper.update(damagedBook,damagedBookUpdateWrapper);

        //将书再次放回可借阅列表
        Book book=new Book();
        book.setBookId(bookid);
        book.setBorrowState("未借阅");
        book.setPlace(damagedBook.getBorrowTime());
        book.setIsbn(bookid.substring(0,bookid.length()-2));
        bookMapper.insert(book);

        return update >= 1;

    }

    @Override
    public List<DamagedBook> getdamaged() {

        QueryWrapper<DamagedBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("repair_time");


        return damagedBookMapper.selectList(queryWrapper);
    }
}
