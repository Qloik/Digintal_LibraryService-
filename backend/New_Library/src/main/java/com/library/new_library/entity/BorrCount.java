package com.library.new_library.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BorrCount对象", description="VIEW")
public class BorrCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private int num;


    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
