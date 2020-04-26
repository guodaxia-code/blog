package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.User;

import java.util.Date;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    @ResultMap("UserMap")
    User getUser(String username, String password);

    @Select("select * from user where id=#{id}")
    @Results(id = "UserMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "nickname",column = "nickname"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "email",column = "email"),
            @Result(property = "avatar",column = "avatar"),
            @Result(property = "type",column = "type"),
            @Result(property = "createTime",column = "createTime"),
            @Result(property = "updateTime",column = "updateTime"),
    })
    User getUserById(String id);
}
