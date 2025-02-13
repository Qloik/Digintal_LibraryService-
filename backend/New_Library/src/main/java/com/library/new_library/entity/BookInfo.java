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
@ApiModel(value="BookInfo对象", description="")
public class BookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String isbn;

    private String bookName;

    private String bookAuthor;

    private String bookTranslation;

    private String bookRepre;

    private String bookPublisher;

    private String bookText;

    private String bookAuthorabout;

    private String bookImg;

    private String authorImg;

    private Integer scorePeopleNumber;

    @ApiModelProperty(value = "评分")
    private Double score;

    private String updateTime;
}
