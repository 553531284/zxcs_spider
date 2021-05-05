package com.dz.novel.component;

import com.dz.novel.entity.Novel;
import com.dz.novel.entity.SpiderError;
import com.dz.novel.utils.NovelTrie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.time.LocalDateTime;

/**
 * @Author: Deng Zhou
 * @Description:
 * @Date: 22:23 2020/1/19
 */
@Component
public class ZxPageProcessor implements PageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ZxPageProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private static String likeUrl;

    private Integer colNum = Integer.MAX_VALUE;

    public ZxPageProcessor setColNum(Integer colNum) {
        this.colNum = colNum;
        return this;
    }

    @Value("${zx.like}")
    public void setLikeUrl(String likeUrl) {
        ZxPageProcessor.likeUrl = likeUrl;
    }


    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        //分类页爬取当前页小说以及下一页
        if (url.contains("sort")) {
            page.addTargetRequests(page.getHtml().$("#plist dt").links().all());
            String pageUrl = page.getHtml().$("#pagenavi").regex("(?<=</span>\\s{1,10}<a\\shref=\")[\\S]*(?=\")").get();
            if (pageUrl!=null) {
                String pageNum = pageUrl.substring(pageUrl.lastIndexOf("/") + 1);
                if(Integer.parseInt(pageNum)<=colNum){
                    page.addTargetRequest(pageUrl);
                }
            }
            page.setSkip(true);
        }
        //小说页，爬取具体信息，获取点赞数据，获取具体下载文件页
        else if (url.contains("post")) {
            Novel novel = new Novel();
            String outId = page.getUrl().get().split("post/")[1];
            if (NovelTrie.getInstance().find(outId)) {
                LOG.info("outId:".concat(outId).concat("已入库"));
                page.setSkip(true);
                return;
            }
            try {
                novel.setOutId(outId);
                novel.setNovelName(page.getHtml().$("#content h1", "text").regex("(?<=《).*(?=》)").get());
                novel.setNovelAuthor(page.getHtml().$("#content h1", "text").regex("(?<=作者：).*").get());
                novel.setNovelType(SystemInit.zxTypeMap.get(page.getHtml()
                        .$("#content .date a", "href").regex("(?<=sort/)[0-9]*").get()));
                String size = page.getHtml().$("#content p", "text").regex("(?<=【TXT大小】：).*(?=\\s【内容简介】)").get();
                if(StringUtils.isNotBlank(size)){
                    String[] s = size.split(" ");
                    if (s.length > 1) {
                        Double sizeNum = Double.valueOf(s[0]);
                        if ("KB".equals(s[1])) {
                            sizeNum /= 1024;
                        }
                        novel.setNovelSize(sizeNum);
                    }
                }
                String text = page.getHtml().$("#content p", "text").regex("(?<=【内容简介】：).*").get();
                if(StringUtils.isNotBlank(text)){
                    novel.setNovelDetail(text.replaceAll((char) 12288 + "", " ").trim());
                }
                page.putField("novel", novel);
                page.addTargetRequest(likeUrl + outId);
                page.addTargetRequest(page.getHtml().$(".pagefujian .down_2").links().get());
            } catch (Exception e) {
                LOG.error("小说页异常：", e);
                SpiderError spiderError = new SpiderError();
                spiderError.setOutId(outId);
                spiderError.setFailDetail(e.getMessage());
                spiderError.setLastTime(LocalDateTime.now());
                page.putField("spiderError", spiderError);
            }
        }
        //下载文件页下载文件
        else if (url.contains("download")) {
            Novel novel = new Novel();
            novel.setOutId(url.split("id=")[1]);
            novel.setFileOutUrl(page.getHtml().$(".downfile").links().get());
            page.putField("novel", novel);
        }
        //点赞页面
        else if (url.contains(likeUrl)) {
            Novel novel = new Novel();
            novel.setOutId(url.split("id=")[1]);
            String[] like = page.getRawText().split(",");
            novel.setZan(Integer.valueOf(like[0]));
            novel.setCai(Integer.valueOf(like[4]));
            page.putField("novel", novel);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

    }
}
