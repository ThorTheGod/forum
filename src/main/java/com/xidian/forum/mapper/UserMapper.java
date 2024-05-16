package com.xidian.forum.mapper;

import com.xidian.forum.pojo.Fan;
import com.xidian.forum.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thornoir
 * @date 2021/12/20
 * @apiNote
 */
@Repository
public interface UserMapper {

    List<User> getAllUser();

    User getUserById(int id);

    User getUserByUsername(String username);

    void addUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    void addFan(Fan fan);

    Integer isFan(int userId,int fanId);

    void deleteFan(int userId,int fanId);

    List<Fan> getFans(int userId);
}
