package com.xidian.forum.mapper;

import com.xidian.forum.pojo.ArticleCollection;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/24
 * @apiNote
 */
@Repository
public interface ArticleCollectionMapper {

    void addCollect(ArticleCollection articleCollection);

    void deleteCollect(int userId,int articleId);

    List<Integer> findCollectByUserId(int userId);

    List<Integer> findCollectByArticleId(int articleId);
}
