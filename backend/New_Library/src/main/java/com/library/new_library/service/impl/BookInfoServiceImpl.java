package com.library.new_library.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.mapper.BookInfoMapper;
import com.library.new_library.service.BookInfoService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {
	@Autowired
	private BookInfoMapper bookInfoMapper;
	
	public BookInfo queryBookInfo(String isbn) {
		return bookInfoMapper.findByIsbn(isbn);
	}

    @Override
    public boolean updateScore(Object isbn, Object rate, Object count) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setScore(Double.parseDouble(rate.toString()));
        bookInfo.setScorePeopleNumber(Integer.parseInt(count.toString()));

        UpdateWrapper<BookInfo> bookInfoUpdateWrapper = new UpdateWrapper<>();
        bookInfoUpdateWrapper.eq("isbn",isbn.toString());
        int update = bookInfoMapper.update(bookInfo,bookInfoUpdateWrapper);

        if (update>=1)
            return true;
        else
            return false;
    }
}
