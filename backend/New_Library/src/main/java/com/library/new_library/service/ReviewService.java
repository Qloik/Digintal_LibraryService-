package com.library.new_library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.Review;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface ReviewService extends IService<Review> {
	List<Review> queryByUserId(String userId);
	
	List<Review> queryValidByPostId(String postId);

	List<Review> queryAllByPostId(String postId);

	List<Review> findAllInvalid();

	List<Review> findAllValid();

    boolean deleteOneReview(Object userId, Object reviewId);

	boolean checkBannedWords(String content);

	String generateId();
}
