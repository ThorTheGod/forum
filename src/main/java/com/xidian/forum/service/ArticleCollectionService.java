package com.xidian.forum.service;

import com.xidian.forum.mapper.ArticleCollectionMapper;
import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.ArticleCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/24
 * @apiNote
 */
@Service
public class ArticleCollectionService {
    @Autowired
    private ArticleCollectionMapper articleCollectionMapper;
    @Autowired
    private ArticleService articleService;

    /**
     * 新增收藏
     * @param articleCollection
     */
    public void addCollect(ArticleCollection articleCollection){
        articleCollectionMapper.addCollect(articleCollection);
    }

    /**
     * 查询用户收藏了哪些文章(articleId)
     * @param userId
     * @return
     */
    public List<Integer> findCollectByUserId(int userId){
        return articleCollectionMapper.findCollectByUserId(userId);
    }

    /**
     * 查出用户具体收藏的文章
     * @param userId
     * @return
     */
    public List<Article> findCollectArticleByUserId(int userId){
        List<Integer> articleIds = articleCollectionMapper.findCollectByUserId(userId);
        List<Article> articles = new ArrayList<>();
        for(int i=0;i<articleIds.size();i++){
            Article article = articleService.getArticleByArticleId(articleIds.get(i));
            articles.add(article);
        }
        return articles;
    }

    /**
     * 查询该用户的文章共有多少 被收藏
     * @param authorId
     * @return
     */
    public int CountCollectByAuthorId(int authorId){
        //根据用户Id查询其所有文章
        List<Article> articles =articleService.getArticleByUserId(authorId);
        int sum = 0;
        //对每篇文章，查collect表确定其被收藏数，然后累加起来得到用户的 总被收藏
        for(Article article:articles){
            sum+=articleCollectionMapper.findCollectByArticleId(article.getArticleId()).size();
        }
        return sum;
    }


    public Integer ifIsCollect(int userId,int articleId){
        //获得用户收藏的文章id
        List<Integer> collects = articleCollectionMapper.findCollectByUserId(userId);
        if(collects.contains(articleId)){//已收藏返回1
            return 1;
        }else //未收藏返回0
            return 0;
    }


    public void deleteCollect(int userId,int articleId){
        articleCollectionMapper.deleteCollect(userId, articleId);
    }


}
