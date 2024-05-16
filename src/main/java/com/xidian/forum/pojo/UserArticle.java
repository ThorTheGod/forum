package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thornoir
 * @date 2021/12/23
 * @apiNote
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserArticle {

    private String username;
    private String userAvatar;
    private String category;
    private Integer collect;
    private Integer commentCount;
    private Article article;


}
