package com.xidian.forum.mapper;

import com.xidian.forum.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Repository
public interface CategoryMapper {

    List<Category> getAllCategory();

    String getCategoryNameByCategoryId(int id);

    void addCategory(Category category);

    void deleteCategory(int id);

    void updateCategory(Category category);

}
