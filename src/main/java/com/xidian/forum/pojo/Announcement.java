package com.xidian.forum.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author thornoir
 * @date 2021/12/15
 * @apiNote 公告
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    private Integer announcementId;
    private String announcementContent;
    private Date create_time;

}
