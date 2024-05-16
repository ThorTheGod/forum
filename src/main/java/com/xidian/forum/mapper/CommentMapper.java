package com.xidian.forum.mapper;

import com.xidian.forum.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/21
 * @apiNote
 */
@Repository
public interface CommentMapper {

    /**
     * 获取帖子中的所有评论，其中userIdParent非空的评论表示此评论是某id的子评论
     * @return
     */
    List<Comment> getCommentByArticleId(int id);

    Integer getCommentCountByArticleId(int id);

    List<Comment> getReplyByUserId(int userId);

    /**
     * 获取用户的所有评论
     * @return
     */
    List<Comment> getCommentByUserId(int id);

    String getUsernameByCommentId(int id);

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(int id);


}
