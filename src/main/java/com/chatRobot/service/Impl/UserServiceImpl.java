package com.chatRobot.service.impl;

import com.chatRobot.dao.IUserDao;
import com.chatRobot.model.User;
import com.chatRobot.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("userService")
public class UserServiceImpl implements IUserService{

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

    /*public void addUser(int id, String em, String pas, String user, String role, int stat, Date reg, String ip) {

        User user1=new User();
        user1.setId(id);
        user1.setEmail(em);
        user1.setPassword(pas);
        user1.setUsername(user);
        user1.setRole(role);
        user1.setStatus(stat);
        user1.setRegTime(reg);
        user1.setRegIp(ip);

        userDao.addUser(user1);
    }*/
    public User addUser(User user) {
        return this.userDao.addUser(user);
    }

    public User removeUser(long userId) {
        return this.userDao.removeUser(userId);
    }

    public User modifyUser(User user) {
        return this.userDao.modifyUser(user);
    }

}