package com.library.new_library.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.new_library.entity.Posts;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
public interface PostsService extends IService<Posts> {
	List<Posts> queryByUserId(String userId);

    boolean deleteOnePost(Object userId, Object postId);

    List<Posts> findAllValid();

    List<Posts> findAllInvalid();

    boolean changeStatus(String postId);
}
