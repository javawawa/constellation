package com.twosmall.constellation.entity.dao;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 应用配置表
 * </p>
 *
 * @author enzo
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gerp_menu")
public class MenuDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @TableField("create_people")
    private String createPeople;

    @TableField("modify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date modifyTime;

    @TableField("modify_people")
    private String modifyPeople;

    /**
     * 1=已删除 0=未删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 分类id
     */
    @TableField("classify_id")
    private Integer classifyId;

    /**
     * 分类名称
     */
    @TableField("classify_name")
    private String classifyName;

    /**
     * 价格
     */
    @TableField("price")
    private Double price;

    /**
     * 图片
     */
    @TableField("photo")
    private String photo;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;


}
