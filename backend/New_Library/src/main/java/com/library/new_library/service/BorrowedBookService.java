package com.library.new_library.service;

import com.library.new_library.entity.BorrowedBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface BorrowedBookService extends IService<BorrowedBook> {

    List<BorrowedBook> borrowedbyUserid(Object userid);

    List<BorrowedBook> getUnreceived();
    List<BorrowedBook> getReceived();

    boolean postponeborrowed(Object bookid, Object returnTime);

    void returnborrowed(Object borrowId, String status);

    String countborrowed(Object userid);

    boolean changeStatus(String borrowId);

    boolean borrow(Object isbn, Object userid,Object place);

    int countisbnplace(Object isbn, Object place);
}
