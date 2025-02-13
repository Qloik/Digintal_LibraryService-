package com.library.new_library.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="RoomAppointment对象", description="")
public class RoomAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String reserveId;

    private String reserveDate;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userId;

    @ApiModelProperty(value = "门牌号")
    private String roomNumber;

    @ApiModelProperty(value = "(多人研习室，个人研习室，双人研习室)")
    private String roomType;

    @ApiModelProperty(value = "（8：00-12：00、13：00-17：00、19：00-22：00）")
    private String reserveTime;

    @ApiModelProperty(value = "（已预约，未预约）")
    private String roomStatus;


}
