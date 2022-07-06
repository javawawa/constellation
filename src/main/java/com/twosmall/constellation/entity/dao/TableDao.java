package com.twosmall.constellation.entity.dao;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gerp_table")
public class TableDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 桌号
     */
    @TableField("table_num")
    private Integer tableNum;

    /**
     * 桌名
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 二维码
     */
    @TableField("table_code")
    private String tableCode;

    /**
     * 创建人
     */
    @TableField("create_people")
    private String createPeople;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("modify_people")
    private String modifyPeople;

    /**
     * 更新时间
     */
    @TableField("modify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 逻辑删除;1/已删除 0/正常
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
