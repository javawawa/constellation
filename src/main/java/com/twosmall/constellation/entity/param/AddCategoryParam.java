package com.twosmall.constellation.entity.param;

import lombok.Data;

/**
 * <a> 代码千万行 注释第一行 编程不规范 同事两行泪 </a>
 *
 * @author Enzo
 * @date 2022/6/8 10:10
 * @description 新增分类
 */
@Data
public class AddCategoryParam {

    private static final long serialVersionUID = 7912470829847753250L;

    /**
     * 名称
     */
    private String categoryName;

    /**
     * 排序
     */
    private Integer order;


}
