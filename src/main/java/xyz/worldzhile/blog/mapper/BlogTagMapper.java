package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.BlogTag;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

@Mapper
@Repository
public interface BlogTagMapper {

    @Insert("insert into blogtag (id,blogid,tagid) values (#{id},#{blogid},#{tagid}) ")
    void save(BlogTag blogTag);

    @Delete("delete from blogtag where blogid=#{id}")
    void deleteBlogTag(String id);

}
