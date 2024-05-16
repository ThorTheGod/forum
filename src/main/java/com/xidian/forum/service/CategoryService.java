package com.xidian.forum.service;

import com.xidian.forum.mapper.CategoryMapper;
import com.xidian.forum.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategory(){
        return categoryMapper.getAllCategory();
    }



    public void addCategory(Category category){
        categoryMapper.addCategory(category);
    }

    public void updateCategory(Category category){
        categoryMapper.updateCategory(category);
    }

    public void deleteCategory(int id){
        categoryMapper.deleteCategory(id);
    }

}
