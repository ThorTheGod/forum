package com.xidian.forum.mapper;

import com.xidian.forum.pojo.Article;
import com.xidian.forum.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Repository
public interface ArticleMapper {


    /**
     * 查出所有帖子(不包含草稿)，并按时间逆序排序
     * @return
     */
    List<Article> getNewArticle();

    /**
     * 查出所有帖子（不包含草稿），按浏览量排序
     */
    List<Article> getHotArticle();

    /**
     * 查出所有热门帖子（不包含草稿），按时间排序
     * @return
     */
    List<Article> getNiceArticle();

    List<Article> getArticleByCategoryId(int id);

    Article getHotArticleByCategoryId(int cateId);

    /**
     * 查出某用户的所有帖子(不包含草稿)
     * @param id
     * @return
     */
    List<Article> getArticleByUserId(int id);



    /**
     * 查出某用户的所有草稿
     * @return
     */
    List<Article> getAllDraft(int id);

    /**
     * 查出热门帖子，按照时间排序，时间越近的放在List越前面
     * @return
     */
    List<Article> getArticleIfIsNice();

    /**
     * 根据帖子id获取帖子、草稿
     * @param id
     * @return
     */
    Article getArticleByArticleId(int id);

    Integer getArticleCountByUserId(int id);


    User getUserByArticleId(int id);


    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(int id);

    List<Article> search(String info);

}
