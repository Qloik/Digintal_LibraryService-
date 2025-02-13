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
@ApiModel(value="BorrowedBook对象", description="")
public class BorrowedBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借阅条目（递增）")
    @TableId
    private String borrowId;

    private String userId;

    private String bookId;

    private String bookName;

    private String isbn;

    private String borrowTime;

    @ApiModelProperty(value = "应还时间")
    private String returnTime;

    @ApiModelProperty(value = "实际还书时间")
    private String backTime;

    @ApiModelProperty(value = "（已归还，未归还）")
    private String borrowStatus;



}
