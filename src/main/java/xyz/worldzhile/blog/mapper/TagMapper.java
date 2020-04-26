package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    @Insert("insert into tag (id,name) values (#{id},#{name}) ")
    void save(Tag tag);

    @Select("select * from tag where id=#{id}")
    @Results(id = "TagMap",value = {
            @Result(id = true,property = "id",column = "id"),
//            @Result(property = "roles" ,column="uid",many = @Many(select = "xyz.worldzhile.dao.RoleDao.findAllByUid",fetchType = FetchType.EAGER))
    })
    Tag get(String id);

    @Select("select * from tag limit #{start},#{pageCount}")
    List<Tag> list(PageBean pageBean);

    @Update("update tag set name=#{name}  where id=#{id}")
    void update(Tag tag);

    @Delete("delete from tag where id=#{id}")
    void deleteTag(String id);

    @Select("select count(id) from tag")
    Integer count();

    @Select("select * from tag where name=#{name}")
    Tag getTagByName(String name);

    @Select("select * from tag ")
    List<Tag> all();


    @Select(" select tag.*,blogtag.blogid,blogtag.tagid from tag " +
            "    left join blogtag " +
            "    on tag.id=blogtag.tagid " +
            "    where blogtag.blogid=#{id} ")
    List<Tag> findTagsByBlogId(String id);

    @Select("select * from ( " +
            " select tagid,count(*) count  from blogtag GROUP BY tagid  ORDER BY count(*)  desc limit 0,#{size} ) new " +
            " left join tag " +
            " on 1=1 " +
            " where tag.id=new.tagid ")
    List<Tag>  indexShow(int size);
}
