package com.library.new_library.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.BookInfo;
import org.springframework.stereotype.Component;

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
public interface BookInfoMapper extends BaseMapper<BookInfo> {
	@Select("select * from book_info where isbn=#{isbn}")
	@ResultType(BookInfo.class)
    public BookInfo findByIsbn(@Param(value = "isbn")String isbn);
}
