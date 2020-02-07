package com.dz.novel.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dz.novel.entity.Novel;
import com.dz.novel.entity.SpiderError;
import com.dz.novel.mapper.NovelMapper;
import com.dz.novel.mapper.SpiderErrorMapper;
import com.dz.novel.utils.FileHelper;
import com.dz.novel.utils.NovelTrie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDateTime;

/**
 * @Author: Deng Zhou
 * @Description:
 * @Date: 14:50 2020/1/23
 */
@Component
public class ZxPipeline implements Pipeline {

    private static final Logger LOG = LoggerFactory.getLogger(ZxPipeline.class);

    @Value("${file.base}")
    private String base;

    @Autowired
    private NovelCache novelCache;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private SpiderErrorMapper spiderErrorMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        SpiderError spiderError = resultItems.get("spiderError");
        if (spiderError != null) {
            spiderErrorMapper.insertOrUpdate(spiderError);
            return;
        }
        Novel novel = resultItems.get("novel");
        LOG.info("novel:" + novel.toString());
        if (StringUtils.isNotBlank(novel.getNovelName())) {
            novel.setFileStatus("0");
            novel.setLikeStatus("0");
            novel.setCreatTime(LocalDateTime.now());
            novel.setLastTime(novel.getCreatTime());
            novelCache.put(novel.getOutId(), novel);
            int insert = novelMapper.insert(novel);
            if (insert != 0) {
                NovelTrie.getInstance().insert(novel.getOutId());
            }
        } else if (StringUtils.isNotBlank(novel.getFileOutUrl())) {
            try {
                Novel novelSelect = novelCache.get(novel.getOutId());
                if (novelSelect == null || "1".equals(novelSelect.getFileStatus())) {
                    return;
                }
                String filePath = new StringBuilder().append(base).append(File.separator)
                        .append(novelSelect.getNovelType()).append(File.separator).toString();
                String fileName = novelSelect.getNovelName().concat(".")
                        .concat(novelSelect.getNovelAuthor()).concat(".rar");
                File path = new File(filePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File file = new File(filePath.concat(fileName));
                if (!file.exists()) {
                    FileHelper.downloadZip(novel.getFileOutUrl(), file);
                }
                if (file.exists()) {
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    novel.setFileSize(Double.valueOf(nf.format(file.length() * 1.0 / 1048576)));
                    novel.setFileName(fileName);
                    novel.setFilePath(filePath);
                    novel.setFileStatus("1");
                    novel.setLastTime(LocalDateTime.now());
                    novelMapper.update(novel, Wrappers.<Novel>lambdaUpdate().eq(Novel::getOutId, novel.getOutId()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            novel.setLikeStatus("1");
            novel.setLastTime(LocalDateTime.now());
            novelMapper.update(novel, Wrappers.<Novel>lambdaUpdate().eq(Novel::getOutId, novel.getOutId()));
        }
    }
}
