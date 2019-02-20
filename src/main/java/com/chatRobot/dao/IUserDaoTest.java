package com.chatRobot.dao;

import com.chatRobot.controller.UserController;
import com.chatRobot.model.User;
import com.chatRobot.serialPort.SerialView;
import com.chatRobot.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

// 加载spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class IUserDaoTest {

    @Autowired
    private IUserDao dao;

    @Autowired
    private IUserService service;

    @Autowired
    private UserController controller;

    @Test
    public void testSelectUser() throws Exception {
//        User user=new User();
//        user.setUsername("李四");
//        user.setPassword("lisi");
//        user.setEmail("3333@qq.com");
//        user.setStatus(0);
//        user.setRole("管理员");
//        user.setRegIp("199.0.2");
//        user.setRegTime(new Date());




    }
    /*@Test
    public void textserialPort () throws Exception{
        new SerialView() ;

    }*/

}