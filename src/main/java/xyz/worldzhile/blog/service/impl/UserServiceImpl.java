package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.User;
import xyz.worldzhile.blog.mapper.UserMapper;
import xyz.worldzhile.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        return userMapper.getUser(username,password);
    }
}
