package com.library.new_library.service;

import com.library.new_library.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface BookService extends IService<Book> {

    public int Count(Object isbn,Object place);
}
