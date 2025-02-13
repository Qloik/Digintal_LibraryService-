package com.library.new_library.mapper;

import com.library.new_library.entity.BorrowedBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Mapper
@Component
public interface BorrowedBookMapper extends BaseMapper<BorrowedBook> {
    @Select("select * from borrowed_book where borrow_status=\"未取书\" order by borrow_time")
    @ResultType(BorrowedBook.class)
    List<BorrowedBook> getUnreceived();

    @Select("select * from borrowed_book where borrow_status=\"已取书\" or borrow_status=\"已过期\" order by borrow_time")
    @ResultType(BorrowedBook.class)
    List<BorrowedBook> getReceived();
}
