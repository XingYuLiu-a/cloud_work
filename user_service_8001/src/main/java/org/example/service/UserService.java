package org.example.service;



import org.example.pojo.User;

public interface UserService {

    User getUserInfo(Integer userId);
    User login(User user);

    User register(User user);


}
