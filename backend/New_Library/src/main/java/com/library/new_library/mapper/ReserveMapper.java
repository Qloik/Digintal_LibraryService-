package com.library.new_library.mapper;

import com.library.new_library.entity.Reserve;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fyf
 * @since 2023-05-31
 */
@Mapper
@Component
public interface ReserveMapper extends BaseMapper<Reserve> {

}
