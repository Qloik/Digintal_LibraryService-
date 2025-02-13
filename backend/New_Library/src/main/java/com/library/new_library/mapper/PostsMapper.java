package com.library.new_library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.Posts;
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
public interface PostsMapper extends BaseMapper<Posts> {
	@Select("select * from posts where status=\"发布成功\" order by time desc")
	@ResultType(Posts.class)
    List<Posts> findAllValid();

	@Select("select * from posts where status=\"未通过审核\" order by time desc")
	@ResultType(Posts.class)
	List<Posts> findAllInvalid();
	
	@Select("select * from posts where user_id=#{user_id} order by time desc")
	@ResultType(Posts.class)
    List<Posts> findByUserId(@Param(value = "user_id")String userID);
}
