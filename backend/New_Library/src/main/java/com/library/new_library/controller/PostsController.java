package com.library.new_library.controller;


import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.Posts;
import com.library.new_library.entity.Review;
import com.library.new_library.service.PostsService;
import com.library.new_library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/posts")
public class PostsController {
	@Autowired
	private PostsService postsService;
	@Autowired
	private ReviewService reviewService;

	@UserLoginToken
	@PostMapping("/save")
	public boolean save(@RequestBody Posts param) {
		// id 生成方式与 review 相同
		param.setPostId(reviewService.generateId());
		boolean result;
		if(reviewService.checkBannedWords(param.getContent())){
			param.setStatus("未通过审核");
			result=false;
		}
		else{
			if(reviewService.checkBannedWords(param.getPostname())){
				param.setStatus("未通过审核");
				result=false;
			}
			else{
				param.setStatus("发布成功");
				result=true;
			}
		}
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
		String time = df.format(date);
		param.setTime(time);
		postsService.save(param);
		return result;
	}

	@PostMapping("/changeOnePost")
	public boolean changeOnePost(@RequestParam Map<String, Object> param){
		String postId=param.get("postId").toString();
		String postName=param.get("postName").toString();
		String content=param.get("content").toString();
		if(reviewService.checkBannedWords(content)||reviewService.checkBannedWords(postName))
			return false;
		Posts post=postsService.getById(postId);
		post.setContent(content);
		post.setPostname(postName);
		post.setStatus("发布成功");
		return postsService.updateById(post);
	}
	
	@GetMapping("/getValid")
	public List<Posts> getValid(){
		return postsService.findAllValid();
	}

	@GetMapping("/getInvalid")
	public List<Posts> getInvalid() { return postsService.findAllInvalid(); }

	@GetMapping("/getallpost")
	public List<Posts> getallpost() { return postsService.list(); }


	@PostMapping("/changeStatus")
	public boolean changeStatus(@RequestParam String postId) { return postsService.changeStatus(postId); }
	
	@UserLoginToken
	@PostMapping("/queryByUserId")
	public List<Posts> queryByUserId(@RequestParam String userId){
		return postsService.queryByUserId(userId);
	}
	
	// 获取一个特定的帖子
	@PostMapping("/queryByPostId")
	public Posts queryByPostId(@RequestParam String postId){
		return postsService.getById(postId);
	}

	
	@PostMapping("/count")
	public int count(@RequestParam String postId) {
		return reviewService.queryValidByPostId(postId).size();
	}

	@UserLoginToken
	@PostMapping("/deleteOnePost")
	public boolean deleteOnePost(@RequestParam Map<String, Object> param) {
		// 删除帖子，但不影响下面的评论
		List<Review> reviews = reviewService.queryAllByPostId(param.get("postId").toString());
		for(Review review : reviews){
			review.setPostId("deleted");
		}
		reviewService.updateBatchById(reviews);
		return postsService.deleteOnePost(param.get("userId"),param.get("postId"));
	}
}
