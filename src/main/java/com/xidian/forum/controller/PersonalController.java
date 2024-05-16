package com.xidian.forum.controller;


import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.Comment;
import com.xidian.forum.pojo.User;
import com.xidian.forum.pojo.UserComment;
import com.xidian.forum.service.ArticleCollectionService;
import com.xidian.forum.service.ArticleService;
import com.xidian.forum.service.CommentService;
import com.xidian.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/27
 * @apiNote
 */
@Controller
public class PersonalController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCollectionService articleCollectionService;

    /**
     * 个人中心
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/personal_center.html")
    public String toPersonal(Model model,HttpSession session){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        session.setAttribute("articles",articleService.getArticleByUserId(userId));
        session.setAttribute("articleClick",articleService.getAllClickByUserId(userId));
        session.setAttribute("articleCount",articleService.getArticleCountByUserId(userId));
        session.setAttribute("draftCount",articleService.getAllDraft(userId).size());
        session.setAttribute("collectCount",articleCollectionService.findCollectByUserId(userId).size());
        //用户给别人文章的评论
        session.setAttribute("commentOtherCount",commentService.getCommentByUserId(userId).size());
        //别人给用户的回复
        session.setAttribute("replyCount",commentService.getReplyByUserId(userId).size());
        //用户的粉丝
        session.setAttribute("fansCount",userService.getFans(userId).size());
        return "personal_center";
    }

    @GetMapping("/personal_draft.html")
    public String toDraft(HttpSession session,Model model){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        model.addAttribute("user",user);
        session.setAttribute("drafts",articleService.getAllDraft(userId));
        return "personal_draft";
    }


    /**
     * 查看你对别人的评论、回复
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/personal_reply.html")
    public String toReply(Model model,HttpSession session){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //用户对别人的评论
        session.setAttribute("userComments",commentService.getCommentByUserId(userId));
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        return "personal_reply";
    }

    /**
     *查看别人给你的留言
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/personal_comment.html")
    public String toComment(Model model,HttpSession session){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        session.setAttribute("userMsg",commentService.getReplyByUserId(userId));
        return "personal_comment";
    }

    @GetMapping("/personal_collect.html")
    public String toCollect(Model model,HttpSession session){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        session.setAttribute("collectArticles",articleCollectionService.findCollectArticleByUserId(userId));
        return "personal_collect";
    }

    @GetMapping("/personalData.html")
    public String updatePerson(Model model,HttpSession session){
        int userId = (int)session.getAttribute("userId");
        model.addAttribute("user",userService.getUserById(userId));

        return "personalData";
    }


    /**
     * 用户上传头像
     */
    @PostMapping("/uploadAvatar")
    public String uploadAvatar(MultipartFile file,Model model,HttpSession session){
        //保存图片的路径
        String path = System.getProperty("user.dir")+"/upload/";
        //获取图片的后缀类型
        String originaFilename = file.getOriginalFilename();
        String prefix=originaFilename.substring(originaFilename.lastIndexOf(".")+1);

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //新的文件的名字
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd_HHmmss");
        //解决文件名字问题
        String filename = (format.format(new Date())).concat("-forum");
        filename = filename.concat('.'+prefix);
        //把本地文件上传到封装上传文件位置的全路径
        try {
            file.transferTo(new File(realPath +"/"+ filename));
            model.addAttribute("filename",filename);
            int userId = (int)session.getAttribute("userId");
            model.addAttribute("user",userService.getUserById(userId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "personalData";
    }


    /**
     * 个人页面更新用户信息
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/updateUser")
    public String updateUser(User user,Model model){
        userService.updateUser(user);
        model.addAttribute("user",user);
        System.out.println("user:"+user);
        return "/personal_center";
    }


    /**
     * 在个人页面修改自己的评论
     * @param comment
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/updateComment")
    public String updateReply(Comment comment,HttpSession session,Model model){
        commentService.updateComment(comment);
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //重新取出用户对别人的评论
        session.setAttribute("userComments",commentService.getCommentByUserId(userId));
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        return "personal_reply";
    }

    /**
     * 在个人页面处回复他人的留言
     * @param comment
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/reply")
    public String reply(Comment comment,HttpSession session,Model model){
        commentService.addComment(comment);
        User user = userService.getUserById((Integer)session.getAttribute("userId"));
        Integer userId = user.getUserId();
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        //回复了别人，则更新自己对别人的评论数
        session.setAttribute("commentOtherCount",commentService.getCommentByUserId(userId).size());
        session.setAttribute("userMsg",commentService.getReplyByUserId(userId));
        return "personal_comment";
    }

    /**
     * 在个人页面取消某文章的收藏
     * @return
     */
    @GetMapping("/cancelCollectAtPer/{id}")
    public String cancelCollectAtPer(@PathVariable("id")int id, HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        articleCollectionService.deleteCollect(userId,id);

        User user = userService.getUserById(userId);
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        //更新文章收藏信息
        session.setAttribute("collectArticles",articleCollectionService.findCollectArticleByUserId(userId));
        session.setAttribute("collectCount",articleCollectionService.findCollectByUserId(userId).size());
        return "personal_collect";
    }
}
