package com.twosmall.constellation.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gep
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gerp_teast")
public class TeastDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Integer id;

    /**
     * 1/morning 2/night
     */
    @TableField("type")
    private Integer type;

    @TableField("content")
    private String content;

    /**
     * 0/未读 1/已读
     */
    @TableField("status")
    private Integer status;

    @TableField("createTime")
    private LocalDateTime createTime;


}
