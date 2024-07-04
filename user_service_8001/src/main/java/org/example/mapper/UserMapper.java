package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where userId = #{userId}")
    User getUserInfo(Integer userId);
    @Select("select * from user where userName=#{userName} and password=#{password}")
    User  login(User user);

    @Insert("insert into user(password, userName, userSex, userImg, delTag, name)" +
            "values (#{password}, #{userName}, #{userSex}, #{userImg}, #{delTag}, #{name})")
    void register(User user);

    @Select("select * from user where userName = #{userName}")
    User getUserByUserName(String userName);
}
