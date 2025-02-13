package com.library.new_library.entity;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fyf
 * @since 2023-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Reserve对象")
public class Reserve implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String isbn;

    private String place;

    private String userId;

    private String retime;
}
