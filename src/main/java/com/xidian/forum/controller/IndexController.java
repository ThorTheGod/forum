package com.xidian.forum.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xidian.forum.pojo.*;
import com.xidian.forum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {


    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleCollectionService articleCollectionService;

    /**
     * 主页，最新帖子+所有分区：0,0
     * @param session
     * @return
     */
    @GetMapping({"/", "mainweb.html"})
    public String toMain(HttpSession session){

        session.setAttribute("userArticles",articleService.getAllArticle(0,0));
        return "mainweb";
    }


    /**
     * 热门帖子+所有分区：1,0
     * @param session
     * @return
     */
    @GetMapping("/hot")
    public String toHot(HttpSession session){
        session.setAttribute("userArticles",articleService.getAllArticle(1,0));
        return "mainweb";
    }

    /**
     * 精华帖子+所有分区：2,0
     * @param session
     * @return
     */
    @GetMapping("/nice")
    public String toNice(HttpSession session){
        session.setAttribute("userArticles",articleService.getAllArticle(2,0));
        return "mainweb";
    }

    @GetMapping("/category/{id}")
    public String toCategory(@PathVariable("id")int id,HttpSession session){
        session.setAttribute("userArticles",articleService.getAllArticle(0,id));
        return "mainweb";
    }





    /**
     * 关于页面
     * @return
     */
    @GetMapping("/about.html")
    public String toAbout(){
        return "about";
    }


    @PostMapping("/search")
    public String search(String info,HttpSession session){
        session.setAttribute("userArticles",articleService.getAllArticle(info));
        return "mainweb";
    }



}
