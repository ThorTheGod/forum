package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thornoir
 * @date 2021/12/22
 * @apiNote
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserComment {

    private String username;
    private String parentUsername;
    private String userAvatar;
    private Comment comment;

}
