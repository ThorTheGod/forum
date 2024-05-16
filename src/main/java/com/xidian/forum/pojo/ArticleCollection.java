package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thornoir
 * @date 2021/12/15
 * @apiNote 收藏
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCollection {
    private Integer collectId;
    private Integer userId;
    private Integer articleId;

}
