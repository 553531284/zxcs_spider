package com.dz.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Deng Zhou
 * @since 2020-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Novel extends Model<Novel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 外部id
     */
    private String outId;

    /**
     * 小说名
     */
    private String novelName;

    /**
     * 小说作者
     */
    private String novelAuthor;

    /**
     * 小说详情
     */
    private String novelDetail;

    /**
     * 小说类型
     */
    private Integer novelType;

    /**
     * 小说大小，单位mb
     */
    private Double novelSize;

    /**
     * 文件大小
     */
    private Double fileSize;

    /**
     * 文件名，格式：小说名.作者
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 外部下载url
     */
    private String fileOutUrl;

    /**
     * 获取状态 0 未获取 1 已获取
     */
    private String fileStatus;

    /**
     * 获取状态 0 未获取 1 已获取
     */
    private String likeStatus;

    /**
     * 赞
     */
    private Integer zan;

    /**
     * 踩
     */
    private Integer cai;

    /**
     * 创建时间
     */
    private LocalDateTime creatTime;

    /**
     * 更新时间
     */
    private LocalDateTime lastTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
