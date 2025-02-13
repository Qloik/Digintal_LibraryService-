package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.new_library.entity.Book;
import com.library.new_library.mapper.BookMapper;
import com.library.new_library.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Override
    public int Count(Object isbn,Object place) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isbn",isbn.toString());
        queryWrapper.eq("place",place.toString());

        List<Book> books = bookMapper.selectList(queryWrapper);
        return books.size();
    }
}
