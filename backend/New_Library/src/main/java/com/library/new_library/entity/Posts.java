package com.library.new_library.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fyf
 * @since 2022-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Posts对象", description="")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String postId;

    private String userId;
    
    private String nickName;

    private String postname;

    private String content;

    private String time;

    private String status;
}
