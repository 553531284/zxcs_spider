package com.dz.novel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dz.novel.entity.Novel;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Deng Zhou
 * @since 2020-01-14
 */
public interface NovelMapper extends BaseMapper<Novel> {

    List<Integer> selectOutId();

}
