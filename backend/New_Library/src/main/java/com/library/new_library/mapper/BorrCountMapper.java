package com.library.new_library.mapper;

import com.library.new_library.entity.BorrCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author fyf
 * @since 2022-11-27
 */
@Mapper
@Component
public interface BorrCountMapper extends BaseMapper<BorrCount> {

}
