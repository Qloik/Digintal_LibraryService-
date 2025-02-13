package com.library.new_library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.Posts;
import com.library.new_library.entity.Review;
import com.library.new_library.mapper.PostsMapper;
import com.library.new_library.service.PostsService;
import com.library.new_library.service.ReviewService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {
	@Autowired
	private PostsMapper postsMapper;
	@Autowired
	private ReviewService reviewService;
	
	@Override
	public List<Posts> findAllValid(){
		return postsMapper.findAllValid();
	}

	@Override
	public List<Posts> findAllInvalid() { return postsMapper.findAllInvalid(); }

	@Override
	public boolean changeStatus(String postId) {
		Posts posts= postsMapper.selectById(postId);
		if(posts.getStatus().equals("未通过审核")){
			posts.setStatus("发布成功");
			int result=postsMapper.updateById(posts);
			return result > 0;
		}
		else if(posts.getStatus().equals("发布成功")){
			posts.setStatus("未通过审核");
			int result=postsMapper.updateById(posts);
			return result > 0;
		}
		return false; //传来的不是非法帖子
	}

	public List<Posts> queryByUserId(String userId){
		return postsMapper.findByUserId(userId);
	}

	@Override
	public boolean deleteOnePost(Object userId, Object postId){
		List<Review> list = reviewService.queryAllByPostId(postId.toString());
		for(Review review : list) {
			if(!reviewService.deleteOneReview(review.getUserId(), review.getReviewId()))
				return false;
		}
		QueryWrapper<Posts> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("post_id",postId.toString());

		int remove = postsMapper.delete(queryWrapper);

		return remove >= 1;
	}
}
