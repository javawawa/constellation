package com.twosmall.constellation.enums;

import lombok.Getter;

/**
 * 请求运势的类型
 *
 * @author gep
 */
public enum FortuneType {
    /**
     * 今日
     */
    TODAY("today"),
    /**
     * 明日
     */
    TOMORROW("tomorrow"),
    /**
     * 周
     */
    WEEK("week"),
    /**
     * 月
     */
    MONTH("month"),
    /**
     * 年
     */
    YEAR("year");

    @Getter
    private final String fType;

    FortuneType(String fType) {
        this.fType = fType;
    }
}
