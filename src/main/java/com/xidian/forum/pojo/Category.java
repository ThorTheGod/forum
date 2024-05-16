package com.xidian.forum.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thornoir
 * @date 2021/12/15
 * @apiNote 分区
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Integer categoryId;
    private String categoryName;


}
