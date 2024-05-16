package com.xidian.forum.controller;

import com.xidian.forum.pojo.User;
import com.xidian.forum.service.ArticleService;
import com.xidian.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author thornoir
 * @date 2021/12/22
 * @apiNote
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;


    @GetMapping("/login.html")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/register.html")
    public String toRegister(){
        return "register";
    }

    @PostMapping("/user/login")
    public String login(String username, String password, Model model, HttpSession session){
        User user = userService.getUserByUsername(username);
        if(user!=null){
            if(password.equals(user.getPassword())){
                session.setAttribute("userId",user.getUserId());
                model.addAttribute("userArticles",articleService.getAllArticle(0,0));
                return "mainweb";
            }else{
                model.addAttribute("msg","密码错误!");
                return "login";
            }
        }else{
            model.addAttribute("msg","该用户不存在！");
            return "login";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/mainweb.html";
    }

    @PostMapping("/user/register")
    public String register(User user,Model model,HttpSession session){
        if(!user.getUsername().equals("")){
            //用户名查重
            if(userService.getUserByUsername(user.getUsername())==null){
                //注册成功
                userService.addUser(user);
                session.setAttribute("userId",user.getUserId());
                session.setAttribute("userArticles",articleService.getAllArticle(0,0));
                return "mainweb";
            }
            else{//重名
                model.addAttribute("msg","用户名已存在！");
                return "register";
            }
        }else {
            model.addAttribute("msg","用户名不能为空！");
            return "register";
        }
    }


}
