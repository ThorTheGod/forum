package com.xidian.forum;

import com.xidian.forum.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class ForumApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    private CommentService commentService;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(commentService.getCommentByUserId(1));
    }

}
