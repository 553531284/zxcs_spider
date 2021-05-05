package com.dz.novel.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dz.novel.entity.Novel;
import com.dz.novel.entity.NovelType;
import com.dz.novel.mapper.NovelMapper;
import com.dz.novel.service.impl.NovelTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Deng Zhou
 * @Description: 定时调度
 * @Date: 23:50 2020/1/19
 */
@Component
public class NovelScheduled {

    @Value("${zx.host}")
    private String host;

    @Value("${zx.like}")
    private String likeUrl;

    @Value("${zx.down}")
    private String downUrl;

    @Autowired
    private NovelTypeServiceImpl novelTypeService;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private ZxPipeline zxPipeline;

    /**
     * @Author: Deng Zhou
     * @Description: 下载最近一列小说，每天凌晨2点
     * @Date: 0:45 2020/1/20
     */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void zxGetOneCol() {
        List<NovelType> list = novelTypeService.list();
        list.forEach(novelType -> Spider.create(new ZxPageProcessor().setColNum(1))
                .addUrl(host.concat("/sort/").concat(novelType.getZxId())).addPipeline(zxPipeline).thread(10).run());
    }

    /**
     * @Author: Deng Zhou
     * @Description: 下载所有小说，每周一0点
     * @Date: 0:45 2020/1/20
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void zxGetAll() {
        List<NovelType> list = novelTypeService.list();
        list.forEach(novelType -> Spider.create(new ZxPageProcessor())
                .addUrl(host.concat("/sort/").concat(novelType.getZxId())).addPipeline(zxPipeline).thread(10).run());
    }

    /**
     * @Author: Deng Zhou
     * @Description: 文件下载失败补偿，1点20开始每两个小时跑一次
     * @Date: 0:45 2020/1/20
     */
    @Scheduled(cron = "0 20 1/2 * * ? ")
    public void zxFileFailRetry() {
        List<Novel> list = novelMapper.selectList(Wrappers.<Novel>lambdaQuery().eq(Novel::getFileStatus, "0"));
        Spider.create(new ZxPageProcessor()).addUrl(list.stream().map(novel -> downUrl.concat(novel.getOutId())).toArray(String[]::new))
                .addPipeline(zxPipeline).thread(10).run();
    }

    /**
     * @Author: Deng Zhou
     * @Description: 点赞失败补偿，1点40开始每两个小时跑一次
     * @Date: 0:45 2020/1/20
     */
    @Scheduled(cron = "0 40 1/2 * * ? ")
    public void zxLikeFailRetry() {
        List<Novel> list = novelMapper.selectList(Wrappers.<Novel>lambdaQuery().eq(Novel::getLikeStatus, "0"));
        Spider.create(new ZxPageProcessor()).addUrl(list.stream().map(novel -> likeUrl.concat(novel.getOutId())).toArray(String[]::new))
                .addPipeline(zxPipeline).thread(10).run();
    }


    /**
     * @Author: Deng Zhou
     * @Description: 点赞刷新，每天凌晨4点，刷新一周前的点赞
     * @Date: 0:45 2020/1/20
     */
    @Scheduled(cron = "0 0 4 * * ? ")
    public void refreshLike() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(-7);
        List<Novel> list = novelMapper.selectList(Wrappers.<Novel>lambdaQuery().le(Novel::getLastTime, localDateTime));
        Spider.create(new ZxPageProcessor()).addUrl(list.stream().map(novel -> likeUrl.concat(novel.getOutId())).toArray(String[]::new))
                .addPipeline(zxPipeline).thread(10).run();
    }

}
