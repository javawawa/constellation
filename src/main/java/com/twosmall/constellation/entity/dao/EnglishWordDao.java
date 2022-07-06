package com.twosmall.constellation.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gerp_english_word")
public class EnglishWordDao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 单词
     */
    @TableField("word")
    private String word;

    /**
     * 音标
     */
    @TableField("phonogram")
    private String phonogram;

    /**
     * 释义
     */
    @TableField("paraphrase")
    private String paraphrase;

    /**
     * 学会状态 0/未学过 1/已学会
     */
    @TableField("study_status")
    private Integer studyStatus;

    /**
     * 笔记本 0/未加入 1/加入
     */
    @TableField("note_flag")
    private Integer noteFlag;

    /**
     * 英式英语发音地址
     */
    @TableField("audio_path_en")
    private String audioPathEn;

    /**
     * 美式英语发音地址
     */
    @TableField("audio_path_am")
    private String audioPathAm;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date createTime;

    @TableField("create_people")
    private String createPeople;

    @TableField("modify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private Date modifyTime;

    @TableField("modify_people")
    private String modifyPeople;

    /**
     * 1=已删除 0=未删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
