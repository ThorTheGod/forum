package com.xidian.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.xidian.forum.pojo.*;
import com.xidian.forum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author thornoir
 * @date 2021/12/22
 * @apiNote
 */
@Controller
public class ArticleController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleCollectionService articleCollectionService;


    @GetMapping("/post.html")
    public String toPost(Model model, HttpSession session){
        if(session.getAttribute("userId")==null){
            return "login";
        }else{
            //嵌入分区
            model.addAttribute("category",categoryService.getAllCategory());
            return "post";
        }
    }

    @PostMapping("/addArticle")
    public String addArticle(Article article,HttpSession session){
        articleService.addArticle(article);
        session.setAttribute("userArticles",articleService.getAllArticle(0,0));
        return "mainweb";
    }

    /**
     * 帖子中的图片上传
     * @param file
     * @return
     * @throws IOException
     */
    //博客图片上传问题
    @RequestMapping("/article/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        //上传路径保存设置

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";
        String name= file.getOriginalFilename();
        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String year = (instance.get(Calendar.YEAR))+"Y";
        String month = (instance.get(Calendar.MONTH) + 1)+"M";
        month = year.concat(month);
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //上传文件地址
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd_HHmmss");
        //解决文件名字问题
        String filename = (format.format(new Date())).concat("-forum");
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ filename));

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("success", 1);
        res.put("message", "upload success!");
        res.put("url","/upload/"+month+"/"+ filename);

        return res;
    }


    /**
     * 展示帖子详情
     * @param id 文章id
     * @return
     */
    @GetMapping("/show/{id}")
    public String getComment(@PathVariable("id")int id,HttpSession session){
        //根据文章id获得文章信息
        Article article = articleService.getArticleByArticleId(id);
        //获得作者id
        Integer authorId = article.getUserId();
        User author = userService.getUserById(authorId);
        //更新点击
        article.setClick(article.getClick()+1);
        //更新数据库
        articleService.updateArticle(article);
        List<UserComment> userComments = commentService.getUserCommentByArticleId(id);

        //作者：名
        session.setAttribute("username",author.getUsername());
        //作者：头像
        session.setAttribute("userAvatar",author.getUserAvatar());
        //作者：粉丝数
        session.setAttribute("fansCount",userService.getFans(authorId).size());
        //作者：原创数
        session.setAttribute("articleCount",articleService.getArticleCountByUserId(authorId));
        //作者：文章被赞数
        session.setAttribute("articleLove",articleService.getAllLoveByUserId(authorId));
        //作者：文章被点击数
        session.setAttribute("articleClick",articleService.getAllClickByUserId(authorId));
        //作者：文章被评论总数
        session.setAttribute("allCommentCount",commentService.getCommentCountByUserId(authorId));
        //作者：其文章被收藏数
        session.setAttribute("collectCount",articleCollectionService.CountCollectByAuthorId(authorId));


        //该文章评论数
        session.setAttribute("commentCount",userComments.size());
        //该文章：信息
        session.setAttribute("article",article);
        session.removeAttribute("haveLoved");
        session.removeAttribute("haveDisliked");
        //用户收藏、关注
        if(session.getAttribute("userId")!=null){//已经登录
            //用户：是否收藏了该文章
            //数据库没查出collect，标记为0表示可以收藏
            session.setAttribute("isCollect",
                    articleCollectionService.ifIsCollect((Integer) session.getAttribute("userId"),
                        article.getArticleId())==0?"0":"1");
            //用户：是否是作者fan
            session.setAttribute("isFan",userService.isFan(authorId,
                    (Integer) session.getAttribute("userId")));

        }else {
            session.removeAttribute("isCollect");
            session.removeAttribute("isFan");
        }
        //评论信息
        session.setAttribute("userComments",userComments);

        //侧边栏热门文章
        //左："1热门"；右：“1灌水”,"2资源","3技术"
        session.setAttribute("discussHotArticle",articleService.getHotArticleByCategoryId(1));
        session.setAttribute("resourceHotArticle",articleService.getHotArticleByCategoryId(2));
        session.setAttribute("techHotArticle",articleService.getHotArticleByCategoryId(3));

//        List<Map<String,String>> lists = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
        return "viewPosts";
    }


    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id")int id,Model model,HttpSession session){
        articleService.deleteArticle(id);
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        model.addAttribute("user",user);
        session.setAttribute("articles",articleService.getArticleByUserId(user.getUserId()));
        return "personal_center";
    }

    @GetMapping("/update/{id}")
    public String toUpdate(@PathVariable("id")int id,Model model){
        Article article = articleService.getArticleByArticleId(id);
        model.addAttribute("article",article);
        model.addAttribute("category",categoryService.getAllCategory());
        return "updatePosts";
    }

    @PostMapping("/updateArticle")
    public String updateArticle(Article article,HttpSession session,Model model){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        //用户信息带有密码，不能放入session
        model.addAttribute("user",user);
        articleService.updateArticle(article);
        //改动了文章，更新session
        session.setAttribute("articles",articleService.getArticleByUserId(user.getUserId()));
        return "personal_center";
    }


    @GetMapping("/addlove")
    public String addLove(HttpSession session){
        Article article = (Article) session.getAttribute("article");
        Integer userId = (Integer) session.getAttribute("userId");
        //登录+未点赞=>点赞
        if(userId==null){//点赞前需登录
            return "login";
        }else if(userId.intValue() == article.getUserId()){//作者本人和点赞用户相同，不能点赞
            return "viewPosts";
        }
        else if(session.getAttribute("haveLoved")=="yes"){//用户已经点过赞，不能再点
            return "viewPosts";
        }
        else{
            article.setLove(article.getLove()+1);
            articleService.updateArticle(article);
            Integer authorId = article.getUserId();
            //点赞只更新了article信息
            //文章总点赞
            session.setAttribute("articleLove",articleService.getAllLoveByUserId(authorId));
            //该文章点赞在article信息中
            session.setAttribute("article",article);
            session.setAttribute("haveLoved","yes");
        }
        return "viewPosts";
    }

    @GetMapping("/cancelLove")
    public String cancelLove(HttpSession session){
        Article article = (Article) session.getAttribute("article");
        Integer userId = (Integer) session.getAttribute("userId");
        article.setLove(article.getLove()-1);
        articleService.updateArticle(article);
        session.setAttribute("articleLove",articleService.getAllLoveByUserId(article.getUserId()));
        session.setAttribute("article",article);
        session.removeAttribute("haveLoved");
        return "viewPosts";
    }


    @GetMapping("/adddislike")
    public String addDislike(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article) session.getAttribute("article");
        if(userId==null){//不喜欢前需登录
            return "login";
        }else if(userId.intValue() == article.getUserId()){//作者本人和不喜欢用户相同，不能点不喜欢
            return "viewPosts";
        }else{
            article.setDislike(article.getDislike()+1);
            articleService.updateArticle(article);
            //不喜欢 只更新了article信息
            session.setAttribute("article",article);
            session.setAttribute("haveDisliked","yes");
            return "viewPosts";
        }
    }

    @GetMapping("/cancelDislike")
    public String cancelDislike(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article) session.getAttribute("article");
        //减少dislike
        article.setDislike(article.getDislike()-1);
        articleService.updateArticle(article);
        session.setAttribute("article",article);
        session.removeAttribute("haveDisliked");

        return "viewPosts";
    }

    @GetMapping("/collect")
    public String addCollect(HttpSession session){
        //当前登录用户id
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article) session.getAttribute("article");
        if(userId==null){//收藏前需先登录
            return "login";
        }else if(userId.intValue() == article.getUserId()){//作者本人和打算收藏的用户相同，不能收藏
            return "viewPosts";
        }else{//若已登录，则添加记录
            articleCollectionService.addCollect(new ArticleCollection(null,userId,
                    article.getArticleId()));
            session.setAttribute("isCollect","1");
            return "viewPosts";
        }
    }

    /**
     * 在帖子页面取消收藏
     * @param session
     * @return
     */
    @GetMapping("/cancelCollect")
    public String cancelCollect(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article) session.getAttribute("article");
        articleCollectionService.deleteCollect(userId,article.getArticleId());
            //取消收藏更新了 是否收藏 属性
        session.removeAttribute("isCollect");
        return "viewPosts";
    }




    @GetMapping("/befan")
    public String addFan(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article) session.getAttribute("article");
        if(userId==null) {//关注前需登录

            return "login";
        }else if(userId.intValue()==article.getUserId()){//用户和文章作者是同一人，不能关注
            return "viewPosts";
        }else{
            userService.addFan(new Fan(null, article.getUserId(), userId));
            session.setAttribute("isFan", userService.isFan(article.getUserId(), userId));
            return "viewPosts";
        }
    }

    @GetMapping("/deletefan")
    public String deleteFan(HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Article article = (Article)session.getAttribute("article");
        userService.deleteFan(article.getUserId(),userId);
        session.removeAttribute("isFan");
        return "viewPosts";
    }

}
