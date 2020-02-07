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
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SpiderError extends Model<SpiderError> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 外部id
     */
    private String outId;

    /**
     * 失败原因
     */
    private String failDetail;

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
