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
@ApiModel(value="Reputation对象", description="")
public class Reputation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "违规条目标识")
    @TableId
    private String reputationId;

    private String bookId;

    @ApiModelProperty(value = "逾期天数")
    private Integer overDate;

    private String userId;

    @ApiModelProperty(value = "违规次数")
    private Integer violationTimes;

    @ApiModelProperty(value = "违规类型")
    private String violationType;


}
