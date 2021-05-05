package com.dz.novel.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dz.novel.component.NovelScheduled;
import com.dz.novel.component.ZxPageProcessor;
import com.dz.novel.component.ZxPipeline;
import com.dz.novel.entity.Novel;
import com.dz.novel.entity.NovelType;
import com.dz.novel.entity.SpiderError;
import com.dz.novel.mapper.NovelMapper;
import com.dz.novel.service.impl.NovelTypeServiceImpl;
import com.dz.novel.service.impl.SpiderErrorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Deng Zhou
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/novel")
@Validated
public class NovelController {

    @Value("${zx.host}")
    private String host;

    @Autowired
    private NovelTypeServiceImpl novelTypeService;

    @Autowired
    private ZxPipeline zxPipeline;

    @Autowired
    private NovelScheduled novelScheduled;

    @Autowired
    private SpiderErrorServiceImpl spiderErrorService;

    @RequestMapping("/zxGetOneCol")
    public void zxGetOneCol() {
        novelScheduled.zxGetOneCol();
    }

    @RequestMapping("/zxGetAll")
    public void zxGetAll() {
        novelScheduled.zxGetAll();
    }

    @RequestMapping("/zxFileFailRetry")
    public void zxFileFailRetry() {
        novelScheduled.zxFileFailRetry();
    }

    @RequestMapping("/zxLikeFailRetry")
    public void zxLikeFailRetry() {
        novelScheduled.zxLikeFailRetry();
    }

    @RequestMapping("/refreshLike")
    public void refreshLike() {
        novelScheduled.refreshLike();
    }

    @RequestMapping("/zxGetByCol")
    public void zxGetByCol(@NotNull Integer num) {
        List<NovelType> list = novelTypeService.list();
        list.forEach(novelType -> Spider.create(new ZxPageProcessor().setColNum(num))
                .addUrl(host.concat("/sort/").concat(novelType.getZxId())).addPipeline(zxPipeline).thread(10).run());
    }

    @RequestMapping("/regetError")
    public void regetError() {
        List<SpiderError> list = spiderErrorService.list();
        Spider.create(new ZxPageProcessor()).addUrl(list.stream().map(novel -> host.concat("/post/").concat(novel.getOutId())).toArray(String[]::new))
                .addPipeline(zxPipeline).thread(10).run();
        spiderErrorService.getBaseMapper().delete(Wrappers.<SpiderError>lambdaQuery().inSql(SpiderError::getOutId, "select out_id from novel"));
    }

}

