package com.library.new_library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.BookInfo;
import com.library.new_library.entity.TopBooks;
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
public interface TopBooksMapper extends BaseMapper<TopBooks> {
	// 只取前3本书的信息（忽略借阅相同的情况）
	@Select("select book_info.* from book_info,top_books where book_info.isbn=top_books.isbn order by borrowed_times desc limit 5")
	@ResultType(BookInfo.class)
	public List<BookInfo> getTopInfos();
    
}
