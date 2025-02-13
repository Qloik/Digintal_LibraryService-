package com.library.new_library.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.new_library.entity.Review;
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
public interface ReviewMapper extends BaseMapper<Review> {
	@Select("select * from review where post_id=#{post_id} and status=\"发布成功\" order by time")
	@ResultType(Review.class)
	List<Review> findValidByPostId(@Param(value = "post_id")String postID);

	@Select("select * from review where status=\"未通过审核\" order by time")
	@ResultType(Review.class)
	List<Review> findAllInvalid();

	@Select("select * from review where status=\"发布成功\" order by time")
	@ResultType(Review.class)
	List<Review> findAllValid();
	
	@Select("select * from review where user_id=#{user_id} order by time desc")
	@ResultType(Review.class)
    List<Review> findByUserId(@Param(value = "user_id")String userID);
}
