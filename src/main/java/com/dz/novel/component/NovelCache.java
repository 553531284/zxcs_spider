package com.dz.novel.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dz.novel.entity.Novel;
import com.dz.novel.mapper.NovelMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Deng Zhou
 * @Description:
 * @Date: 16:16 2020/1/23
 */
@Component
public class NovelCache {

    private LoadingCache<String, Novel> cache;

    @Autowired
    private NovelMapper novelMapper;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build(new CacheLoader<String, Novel>() {
            public Novel load(String outId) {
                return novelMapper.selectOne(Wrappers.<Novel>lambdaQuery().eq(Novel::getOutId, outId));
            }
        });
    }

    public Novel get(String outId) throws Exception {
        return cache.get(outId);
    }

    public void put(String key, Novel novel) {
        cache.put(key, novel);
    }
}
