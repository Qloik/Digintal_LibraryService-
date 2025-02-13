package com.library.new_library.entity;

import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

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
@ApiModel(value = "EntryAppointment对象", description = "")
public class EntryAppointment implements Serializable {

	private static final long serialVersionUID = 1L;

	@MppMultiId
	private String userId;

	@MppMultiId
	private String appointTime;

}
