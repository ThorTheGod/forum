package com.xidian.forum.service;

import com.xidian.forum.mapper.*;
import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.User;
import com.xidian.forum.pojo.UserArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleCollectionMapper articleCollectionMapper;

    /**
     * selectId在controller层定义用于service层选择哪种mapper，而cateId需要调用同一个mapper传不同参数
     * 本方法为获得最新帖子
     * @return
     */
    public List<UserArticle> getAllArticle(int selectId,int cateId){
        List<UserArticle> userArticles = new ArrayList<>();
        String username = null;
        String userAvatar = null;
        String category = null;
        Integer collect = null;
        Integer commentCount = null;
        List<Article> articles = new ArrayList<>();
        //根据需求获取article
        if(cateId==0){//未选择分区，则根据“0最新”,"1热门","2精华"来选择不同的mapper方法
            switch (selectId){
                case 0: articles = articleMapper.getNewArticle();
                        break;
                case 1: articles = articleMapper.getHotArticle();
                        break;
                case 2: articles = articleMapper.getNiceArticle();
                        break;
            }
        }else{//选择了分区，则传入分区id给mapper层
            articles = articleMapper.getArticleByCategoryId(cateId);
        }
        //拼接article与其相关信息，组成UserArticle
        for(Article article:articles){
            //文章的作者名
            User user = articleMapper.getUserByArticleId(article.getArticleId());
            username = user.getUsername();
            userAvatar = user.getUserAvatar();
            //文章的分区名
            category = categoryMapper.getCategoryNameByCategoryId(article.getArticleCategoryId());
            collect = articleCollectionMapper.findCollectByArticleId(article.getArticleId()).size();
            commentCount = commentMapper.getCommentCountByArticleId(article.getArticleId());
            UserArticle userArticle = new UserArticle(username,userAvatar,category,collect,commentCount,
                    article);
            userArticles.add(userArticle);
        }
        return userArticles;
    }

    /**
     * 根据搜索信息查出相关文章
     * @param info
     * @return
     */
    public List<UserArticle> getAllArticle(String info){
        List<UserArticle> userArticles = new ArrayList<>();
        List<Article> articles;
        String username = null;
        String userAvatar = null;
        String category = null;
        Integer collect = null;
        Integer commentCount = null;
        //根据搜索内容查出符合文章
        articles = articleMapper.search(info);
        //每篇文章贴上相关信息
        for(Article article:articles){

            User user = articleMapper.getUserByArticleId(article.getArticleId());
            username = user.getUsername();
            userAvatar = user.getUserAvatar();
            //文章的作者名
            //文章的分区名
            category = categoryMapper.getCategoryNameByCategoryId(article.getArticleCategoryId());
            collect = articleCollectionMapper.findCollectByArticleId(article.getArticleId()).size();
            commentCount = commentMapper.getCommentCountByArticleId(article.getArticleId());
            UserArticle userArticle = new UserArticle(username,userAvatar,category,collect,commentCount,
                    article);
            userArticles.add(userArticle);
        }
        return userArticles;

    }


    public Integer getArticleCountByUserId(int id){
        return articleMapper.getArticleCountByUserId(id);
    }

    /**
     * 查出用户所有文章的总被赞数
     * @param id
     * @return
     */
    public Integer getAllLoveByUserId(int id){
        List<Article> articles = articleMapper.getArticleByUserId(id);
        Integer sum = 0;
        for(Article article:articles){
            sum+=article.getLove();
        }
        return sum;
    }

    /**
     * 查出用户所有文章的总被点击数
     * @param id
     * @return
     */
    public Integer getAllClickByUserId(int id){
        List<Article> articles = articleMapper.getArticleByUserId(id);
        Integer sum = 0;
        for(Article article:articles){
            sum+=article.getClick();
        }
        return sum;
    }

    public List<Article> getArticleByCategoryId(int id){return articleMapper.getArticleByCategoryId(id);}

    /**
     * 获得热门（访问量最高）的某分区的文章
     * @param cateId
     * @return
     */
    public Article getHotArticleByCategoryId(int cateId){
        return articleMapper.getHotArticleByCategoryId(cateId);
    }

    public List<Article> getAllDraft(int id){
        return articleMapper.getAllDraft(id);
    }

    public List<Article> getArticleByUserId(int id){
        return articleMapper.getArticleByUserId(id);
    }

    public Article getArticleByArticleId(int id){
        return articleMapper.getArticleByArticleId(id);
    }

    public void addArticle(Article article){
        articleMapper.addArticle(article);
    }

    public void updateArticle(Article article){
        articleMapper.updateArticle(article);
    }

    public void deleteArticle(int id){
        articleMapper.deleteArticle(id);
    }

    public String getUsernameByArticleId(int id){
        return articleMapper.getUserByArticleId(id).getUsername();
    }

}
