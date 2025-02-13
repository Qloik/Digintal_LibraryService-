package com.library.new_library.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2022-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BookCountCampusAll对象", description="VIEW")
public class BookCountCampusAll implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isbn;

    private String bookName;

    @TableField("count_Jiading")
    private Long countJiading;

    @TableField("count_Siping")
    private Long countSiping;


}
