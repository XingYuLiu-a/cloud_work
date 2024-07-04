package org.example.controller;


import org.example.pojo.User;
import org.example.resp.ResultData;
import org.example.service.UserService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo/{userId}")
    public ResultData getUserInfo(@PathVariable("userId") Integer userId){
        User user = userService.getUserInfo(userId);
        return ResultData.success(user);
    }
    @PostMapping("/login")
    public ResultData login(@RequestBody User user){
        User user1 =  userService.login(user);
        JwtUtils jwtUtils = new JwtUtils();
        if(user1!=null){
            Map<String,Object> map = new HashMap<>();
            map.put("userId", user1.getUserId());
            map.put("userName",user1.getUserName());
            map.put("name",user1.getName());
            String token = jwtUtils.createToken(map);
            user1.setToken(token);
            return ResultData.success(user1);
        }else {
            return ResultData.fail(999,"用户名或密码错误");
        }
    }
    @PostMapping("/register")
    public ResultData register(@RequestBody User user){
        User newUser = userService.register(user);
        if (newUser != null){
            return ResultData.success(newUser);
        }else {
            return ResultData.fail(999,"该用户名已被注册");
        }

    }
}
