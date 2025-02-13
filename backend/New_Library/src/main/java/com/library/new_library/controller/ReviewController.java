package com.library.new_library.controller;


import com.library.new_library.annotation.UserLoginToken;
import com.library.new_library.entity.Review;
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
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@UserLoginToken
	@PostMapping("/save")
	public boolean save(@RequestBody Review param) {
		param.setReviewId(reviewService.generateId());
		// 记录时间
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df2.format(new Date());
		param.setTime(time);
		boolean result;
		if(reviewService.checkBannedWords(param.getContent())){
			param.setStatus("未通过审核");
			result=false;
		}
		else{
			param.setStatus("发布成功");
			result=true;
		}
		reviewService.save(param);
		return result;
	}

	@PostMapping("/changeOneReview")
	public boolean changeOneReview(@RequestParam Map<String, Object> param){
		String reviewId=param.get("reviewId").toString();
		String content=param.get("content").toString();
		if(reviewService.checkBannedWords(content))
			return false;
		Review review=reviewService.getById(reviewId);
		review.setContent(content);
		review.setStatus("发布成功");
		return reviewService.updateById(review);
	}

	@GetMapping("/getInvalid")
	public List<Review> getInvalid(){
		return reviewService.findAllInvalid();
	}

	@GetMapping("/getValid")
	public List<Review> getValid(){
		return reviewService.findAllValid();
	}


	@GetMapping("/getallreview")
	public List<Review> getallreview() { return reviewService.list(); }


	@PostMapping("/changeStatus") // 修改审核状态为另一种
	public boolean changeStatus(@RequestParam Map<String, Object> param){
		String reviewId=param.get("reviewId").toString();
		Review review=reviewService.getById(reviewId);
		if(review.getStatus().equals("未通过审核")){
			review.setStatus("发布成功");
			return reviewService.updateById(review);
		}
		else if(review.getStatus().equals("发布成功")){
			review.setStatus("未通过审核");
			return reviewService.updateById(review);
		}
		return false;
	}

	@PostMapping("/queryByPostId")
	public List<Review> queryByPostId(@RequestParam String postId){
		return reviewService.queryValidByPostId(postId);
	}
	
	@UserLoginToken
	@PostMapping("/queryByUserId")
	public List<Review> queryByUserId(@RequestParam String userId){
		return reviewService.queryByUserId(userId);
	}
	
	@UserLoginToken
	@PostMapping("/deleteOneReview")
	public boolean deleteOneReview(@RequestParam Map<String, Object> param) {
		return reviewService.deleteOneReview(param.get("userId"),param.get("reviewId"));
	}
}
