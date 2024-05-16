package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thornoir
 * @date 2021/12/25
 * @apiNote
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fan {

    private Integer initId;
    private Integer userId;
    private Integer fanId;

}
