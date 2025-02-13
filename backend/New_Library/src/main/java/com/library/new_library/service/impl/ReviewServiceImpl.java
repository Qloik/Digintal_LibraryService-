package com.library.new_library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.new_library.entity.Review;
import com.library.new_library.mapper.ReviewMapper;
import com.library.new_library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {
	@Autowired
	private ReviewMapper reviewMapper;

	@Override
	public List<Review> queryByUserId(String userId){
		return reviewMapper.findByUserId(userId);
	}

	@Override
	public List<Review> queryValidByPostId(String postId){
		return reviewMapper.findValidByPostId(postId);
	}

	@Override
	public List<Review> queryAllByPostId(String postId){
		QueryWrapper<Review> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("post_id",postId);

		return reviewMapper.selectList(queryWrapper);
	}

	@Override
	public List<Review> findAllInvalid() {
		return reviewMapper.findAllInvalid();
	}

	@Override
	public List<Review> findAllValid() {
		return reviewMapper.findAllValid();
	}

	@Override
	public boolean deleteOneReview(Object userId, Object reviewId){

		QueryWrapper<Review> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("review_id",reviewId.toString());

		int remove =reviewMapper.delete(queryWrapper);
		return remove >= 1;

	}

	/**
	 *
	 * @param content: 评论内容
	 * @return boolean 是否有敏感词，若有，返回 true
	 */
	@Override
	public boolean checkBannedWords(String content) {


		boolean result=false; // python 判断的结果
		try {
			// 获取 resources 下的 python 文件路径
			ClassPathResource classPathResource = new ClassPathResource("static/DetectSensitiveWord.exe");
			// 为保证地址正确，从 from 位置截取到 .exe 所在文件夹
			String totalPath = classPathResource.getURL().getPath();
			String folderPath = totalPath.substring(1,totalPath.lastIndexOf("/"));
			String pyPath = totalPath.substring(1);
			System.out.println(pyPath);

			// 传入 python 脚本的地址和参数
			String cmd="cmd /c "+pyPath+" "+content+" "+folderPath;
			// 执行 python 文件，并传入参数
			Process process = Runtime.getRuntime().exec(cmd);
			// 获取 python 输出字符串作为输入流被 Java 读取
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String actionStr;
			while ((actionStr=in.readLine())!=null){
				if (actionStr.equals("True")) { // 含有敏感词
					System.out.println(actionStr);
					result=true;
				}
			}

			in.close();
			// java 代码中的 process.waitFor() 返回值为 0 表示我们执行 .exe 文件成功，
			// 返回值为 1 表示执行 .exe 文件失败，这和我们通常意义上见到的 0 与 1 定义正好相反
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	// 设置id生成方式，xxxx(年)xx(月)xx(日)xx(时)xx(分)xx(秒)xxxx(随机数)
	@Override
	public String generateId() {
		SecureRandom random = new SecureRandom();
		Date date = new Date(); // 获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // 设置日期格式
		StringBuilder id = new StringBuilder(df.format(date));
		for(int i=0;i<4;i++) {
			int randomInt = random.nextInt(10); // 生成范围0-9
			id.append(randomInt);
		}
		return id.toString();
	}

}
