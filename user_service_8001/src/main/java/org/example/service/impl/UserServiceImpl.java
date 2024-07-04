package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    @CircuitBreaker(name = "user-service", fallbackMethod = "defaultUser")
    public User getUserInfo(Integer userId) {
        if (userId <= 0){
            throw new RuntimeException("Id必须为正数");
        }
        return userMapper.getUserInfo(userId);
    }

    @Override
    @CircuitBreaker(name = "user-service", fallbackMethod = "defaultUser")
    public User login(User user) {
        return userMapper.login(user);
    }
    @Override
    @CircuitBreaker(name = "user-service", fallbackMethod = "defaultUser")
    public User register(User user) {
        User newUser = userMapper.getUserByUserName(user.getUserName());
        if (newUser == null){
            user.setDelTag(1);
            userMapper.register(user);
            return user;
        }else {
            return null;
        }
    }


    // Fallback methods

    public User defaultUser(Throwable t) {

        return null;
    }

}
