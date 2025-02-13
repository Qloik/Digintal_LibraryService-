package com.library.new_library.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.BookCount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
@Mapper
@Component
public interface BookCountMapper extends BaseMapper<BookCount> {
	@Select("select * from book_count where isbn=#{isbn}")
	@ResultType(BookCount.class)
	BookCount findByIsbn(@Param(value = "isbn")String isbn);
	
	@Update("update book_count set count = count+#{add} where isbn=#{isbn}")
	boolean updateByIsbn(@Param(value = "isbn")String isbn, @Param(value = "add")int add);
}
