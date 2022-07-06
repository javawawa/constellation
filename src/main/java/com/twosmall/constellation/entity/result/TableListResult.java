package com.twosmall.constellation.entity.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <a> 代码千万行 注释第一行 编程不规范 同事两行泪 </a>
 *
 * @author Enzo
 * @date 2022-07-04 16:34
 * @description 餐桌列表
 */
@NoArgsConstructor
@Data
public class TableListResult {

    @JsonProperty("id")
    private Integer id;

    /**
     * 餐桌名称
     */
    @JsonProperty("tableName")
    private String tableName;

    /**
     * 二维码
     */
    @JsonProperty("tableCode")
    private String tableCode;
}
