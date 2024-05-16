package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author thornoir
 * @date 2021/12/15
 * @apiNote 文章
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer articleId;
    private String articleTitle;
    private Integer articleCategoryId;
    private Integer userId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activeTime;
    private Integer love;
    private Integer dislike;
    private Integer click;
    private Integer isDraft;
    private Integer isNice;



}
