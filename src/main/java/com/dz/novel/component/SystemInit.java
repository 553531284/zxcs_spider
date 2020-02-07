package com.dz.novel.component;

import com.dz.novel.entity.NovelType;
import com.dz.novel.mapper.NovelMapper;
import com.dz.novel.service.impl.NovelTypeServiceImpl;
import com.dz.novel.utils.NovelTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Deng Zhou
 * @Description: 系统初始化
 * @Date: 15:50 2020/1/22
 */
@Component
public class SystemInit implements ApplicationRunner {

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private NovelTypeServiceImpl novelTypeService;

    public static Map<String, Integer> zxTypeMap = new HashMap<>();

    @Override
    public void run(ApplicationArguments args) {
        BuildTrie();
        NovelTypeConverter();

    }

    /**
     * @Author: Deng Zhou
     * @Description: 构建字典树
     * @Date: 22:43 2020/1/22
     */
    private void BuildTrie() {
        List<Integer> outIds = novelMapper.selectOutId();
        outIds.forEach(e -> NovelTrie.getInstance().insert(e.toString()));
    }

    /**
     * @Author: Deng Zhou
     * @Description: 类型映射
     * @Date: 22:47 2020/1/22
     */
    private void NovelTypeConverter() {
        List<NovelType> list = novelTypeService.list();
        list.forEach(novelType -> zxTypeMap.put(novelType.getZxId(), novelType.getId()));
    }

}