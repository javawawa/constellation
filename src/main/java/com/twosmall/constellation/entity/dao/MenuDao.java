package com.twosmall.constellation.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_people")
    private String createPeople;

    @TableField("modify_time")
    private LocalDateTime modifyTime;

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
    private Long classifyId;

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
