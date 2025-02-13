package com.library.new_library.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Review对象", description="")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论的id")
    @TableId
    private String reviewId;

    private String postId;

    private String userId;

    private String content;

    private String time;

    private String nickName;

    private String status;
}
