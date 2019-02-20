package com.chatRobot.service;

import com.chatRobot.model.User;

import java.util.Date;

public interface IUserService {

    public User selectUser(long userId);

    //public void addUser1(int id, String em, String pas, String user, String role, int stat, Date reg, String ip);

    public User addUser(User user);
    public User removeUser(long userId);

    public User modifyUser(User user);

}