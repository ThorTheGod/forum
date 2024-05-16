package com.xidian.forum.service;

import com.xidian.forum.mapper.ArticleMapper;
import com.xidian.forum.mapper.CommentMapper;
import com.xidian.forum.mapper.UserMapper;
import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.Comment;
import com.xidian.forum.pojo.User;
import com.xidian.forum.pojo.UserComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/21
 * @apiNote
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 某文章的所有评论及其评论用户
     * @param id
     * @return
     */
    public List<UserComment> getUserCommentByArticleId(int id){
        List<Comment> comments = commentMapper.getCommentByArticleId(id);
        List<UserComment> userComments = new ArrayList<>();
        String username = null;
        String parentUsername = null;
        String userAvatar = null;
        for(Comment comment:comments){
            User user = userMapper.getUserById(comment.getUserId());
            username =user.getUsername();
            userAvatar = user.getUserAvatar();
            parentUsername = null;

            if(comment.getUserIdParent()!=null){//存在父id,则评论对象为父id
                parentUsername = userMapper.getUserById(comment.getUserIdParent()).getUsername();
            }
            UserComment userComment =
                    new UserComment(username,parentUsername,userAvatar,comment);
            userComments.add(userComment);
        }

        return userComments;
    }

    /**
     * 某用户所有文章的所有评论数
     * @param id
     * @return
     */
    public Integer getCommentCountByUserId(int id){
        List<Article> articles = articleMapper.getArticleByUserId(id);
        Integer sum = 0;
        for(Article article:articles){
            sum+=commentMapper.getCommentCountByArticleId(article.getArticleId());
        }
        return sum;
    }

    /**
     * 别人给用户的留言： ？ 评论 你(parentId)
     * @param userId
     * @return
     */
    public List<UserComment> getReplyByUserId(int userId){
        List<Comment> comments = commentMapper.getReplyByUserId(userId);
        List<UserComment> userComments = new ArrayList<>();
        String username = null;
        String parentUsername = null;
        String userAvatar = null;
        for(Comment comment:comments){
            User user = userMapper.getUserById(comment.getUserId());
            //username作为 ？
            username = user.getUsername();
            userAvatar = user.getUserAvatar();
            //parenUsername不需要用，因此用来存放文章的标题
            parentUsername = articleMapper.getArticleByArticleId(comment.getArticleId()).getArticleTitle();
            UserComment userComment = new UserComment(username,parentUsername,userAvatar,comment);
            userComments.add(userComment);
        }
        return userComments;
    }

    /**
     * 某用户的所有评论：你 评论了 ？
     * @param id
     * @return
     */
    public List<UserComment> getCommentByUserId(int id){
        List<Comment> comments = commentMapper.getCommentByUserId(id);
        List<UserComment> userComments = new ArrayList<>();
        String username = null;
        String parentUsername = null;
        String userAvatar = null;
        for(Comment comment:comments){

            userAvatar = userMapper.getUserById(id).getUserAvatar();
            //个人中心的 评论栏 不需要用户名，因此可以用来存储 文章标题
            username = articleMapper.getArticleByArticleId(comment.getArticleId()).getArticleTitle();
            if(comment.getUserIdParent()!=null){//存在父id则取出父用户昵称
                User user = userMapper.getUserById(comment.getUserIdParent());
                parentUsername = user.getUsername();
            }else{//不存在父id，表明是对文章的评论，评论对象为文章作者
                parentUsername = articleMapper.getUserByArticleId(comment.getArticleId()).getUsername();
            }
            UserComment userComment = new UserComment(username,parentUsername,userAvatar,comment);
            userComments.add(userComment);
        }
        return userComments;
    }

    public void addComment(Comment comment){
        commentMapper.addComment(comment);
    }

    public void updateComment(Comment comment){
        commentMapper.updateComment(comment);
    }

    public void deleteComment(int id){
        commentMapper.deleteComment(id);
    }



}
