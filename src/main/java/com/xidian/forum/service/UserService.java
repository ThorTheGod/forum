package com.xidian.forum.service;

import com.xidian.forum.mapper.UserMapper;
import com.xidian.forum.pojo.Fan;
import com.xidian.forum.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUser(){
        return userMapper.getAllUser();
    }

    public User getUserById(int id){
        return userMapper.getUserById(id);
    }

    public User getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }

    public void addUser(User user){
        userMapper.addUser(user);
    }

    public void deleteUser(int id){
        userMapper.deleteUserById(id);
    }

    public void updateUser(User user){
        userMapper.updateUser(user);
    }

    public void addFan(Fan fan){
        userMapper.addFan(fan);
    }

    public Integer isFan(int userId,int fanId){
        return userMapper.isFan(userId, fanId);
    }

    public void deleteFan(int userId,int fanId){
        userMapper.deleteFan(userId,fanId);
    }

    public List<Fan> getFans(int userId){
        return userMapper.getFans(userId);
    }

}
