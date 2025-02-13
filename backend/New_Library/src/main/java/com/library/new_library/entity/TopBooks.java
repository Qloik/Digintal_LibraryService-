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
@ApiModel(value="TopBooks对象", description="")
public class TopBooks implements Serializable {

    private static final long serialVersionUID = 1L;
    
//    private BookInfo bookInfo;
    
    @TableId
    private String isbn;

    private String bookName;

    private Integer borrowedTimes;


}
