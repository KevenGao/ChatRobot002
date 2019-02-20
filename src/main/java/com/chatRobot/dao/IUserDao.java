package com.chatRobot.dao;

import com.chatRobot.model.User;

public interface IUserDao {

    User selectUser(long id);

    User addUser(User user);

    User removeUser(long id);

    User modifyUser(User user);

}