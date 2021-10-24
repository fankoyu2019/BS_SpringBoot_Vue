package edu.ujn.service.impl;

import edu.ujn.pojo.User;
import edu.ujn.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Test
    public void test1(){
        User admin = userService.login("admin", "123456");
        System.out.println("admin = " + admin);
    }
}