package com.dz.novel.mapper;

import com.dz.novel.entity.SpiderError;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Deng Zhou
 * @since 2020-02-07
 */
public interface SpiderErrorMapper extends BaseMapper<SpiderError> {

    void insertOrUpdate(SpiderError spiderError);

}
