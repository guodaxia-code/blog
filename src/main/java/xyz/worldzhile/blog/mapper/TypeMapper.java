package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {

    @Insert("insert into type (id,name) values (#{id},#{name}) ")
    void save(Type type);

    @Select("select * from type where id=#{id}")
    @Results(id = "TypeMap",value = {
            @Result(id = true,property = "id",column = "id"),
//            @Result(property = "roles" ,column="uid",many = @Many(select = "xyz.worldzhile.dao.RoleDao.findAllByUid",fetchType = FetchType.EAGER))
    })
    Type get(String id);

    @Select("select * from type limit #{start},#{pageCount}")
    List<Type> list(PageBean pageBean);

    @Select("select * from type ")
    List<Type> all();

    @Update("update type set name=#{name}  where id=#{id}")
    void update(Type type);

    @Delete("delete from type where id=#{id}")
    void deleteType(String id);

    @Select("select count(id) from type")
    Integer count();

    @Select("select * from type where name=#{name}")
    Type getTypeByName(String name);

    @Select(" select  type.*,count from type, " +
            "   (SELECT count(*) count ,name FROM  blog " +
            "    left join type " +
            "    on blog.blog_typeid=type.id " +
            "    GROUP BY name ORDER BY count(*) DESC limit 0,#{size}) new " +
            "    where type.name=new.name ")
    List<Type> indexShow(int size);
}
