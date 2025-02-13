package com.library.new_library.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author fyf
 * @since 2022-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ForborrowedBookBookinfo对象", description="VIEW")
public class ForborrowedBookBookinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String bookId;

    private String bookName;

    private String isbn;

    @ApiModelProperty(value = "已借阅、未借阅、不可借阅")
    private String borrowState;

    private String place;


}
