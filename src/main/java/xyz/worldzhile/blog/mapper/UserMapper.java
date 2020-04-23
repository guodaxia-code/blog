package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.User;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    User getUser(String username, String password);
}
