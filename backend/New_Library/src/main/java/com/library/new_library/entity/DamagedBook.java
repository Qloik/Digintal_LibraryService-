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
@ApiModel(value = "DamagedBook对象", description = "")
public class DamagedBook implements Serializable {

	private static final long serialVersionUID = 1L;
	@TableId
	private String bookId;

	private String userId;

	private String damageTime;

	private String repairTime;

	private String borrowTime;

	private String bookName;

}
