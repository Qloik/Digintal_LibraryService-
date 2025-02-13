package com.library.new_library.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author fyf
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BookCountCampus对象", description="VIEW")
public class BookCountCampus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isbn;

    private String bookName;

    @TableField("count_Jiading")
    private Long countJiading;

    @TableField("count_Siping")
    private Long countSiping;


}
