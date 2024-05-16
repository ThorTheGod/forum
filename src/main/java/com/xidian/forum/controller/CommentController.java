package com.xidian.forum.controller;

import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.Comment;
import com.xidian.forum.pojo.User;
import com.xidian.forum.pojo.UserComment;
import com.xidian.forum.service.ArticleService;
import com.xidian.forum.service.CommentService;
import com.xidian.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/28
 * @apiNote
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    /**
     * 在帖子页面评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comment")
    public String addComment(Comment comment, HttpSession session){

        if(session.getAttribute("userId")!=null){//评论前需登录
            commentService.addComment(comment);
            //文章id
            Integer articleId = comment.getArticleId();
            Article article= (Article) session.getAttribute("article");
            article.setActiveTime(new Date());
            articleService.updateArticle(article);
            //作者id
            Integer authorId = article.getUserId();
            List<UserComment> userComments = commentService.getUserCommentByArticleId(articleId);
            //更新评论相关信息
            session.setAttribute("allCommentCount",commentService.getCommentCountByUserId(authorId));
            session.setAttribute("commentCount",userComments.size());
            session.setAttribute("userComments",userComments);

            return "viewPosts";
        }else{
            return "login";
        }
    }

    /**
     * 帖子页面：回复他人评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/replyPost")
    public String replyPost(Comment comment,HttpSession session){
        commentService.addComment(comment);
        if(session.getAttribute("userId")!=null) {//评论前需登录
            Integer articleId = comment.getArticleId();
            Article article= (Article) session.getAttribute("article");
            article.setActiveTime(new Date());
            articleService.updateArticle(article);
            //作者id
            Integer authorId = article.getUserId();
            List<UserComment> userComments = commentService.getUserCommentByArticleId(articleId);
            //更新评论相关信息
            session.setAttribute("allCommentCount",commentService.getCommentCountByUserId(authorId));
            session.setAttribute("commentCount",userComments.size());
            session.setAttribute("userComments",userComments);

            return "viewPosts";
        }else{
            return "login";
        }
    }

    /**
     * 个人页面删除自己的评论
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id")int id, HttpSession session, Model model){
        //删除评论
        commentService.deleteComment(id);

        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        List<UserComment> userComments = commentService.getCommentByUserId(userId);
        //更新用户对别人的评论
        session.setAttribute("userComments",userComments);
        //更新用户给别人文章的评论数
        session.setAttribute("commentOtherCount",userComments.size());
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        return "personal_reply";
    }
}
