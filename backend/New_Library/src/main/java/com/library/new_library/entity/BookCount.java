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
 * @since 2022-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BookCount对象", description="")
public class BookCount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId
    private String isbn;

    @ApiModelProperty(value = "当前序号数")
    private Integer count;


}
