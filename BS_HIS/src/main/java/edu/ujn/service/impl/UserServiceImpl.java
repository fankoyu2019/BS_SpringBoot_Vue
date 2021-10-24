package edu.ujn.service.impl;

import edu.ujn.mapper.UserMapper;
import edu.ujn.pojo.User;
import edu.ujn.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        try {
            User user = new User(username,password);
            User user1 = userMapper.findByUsernameAndPassword(user);
            return user1;
        }catch (Exception e){
            return null;
        }
    }
}
