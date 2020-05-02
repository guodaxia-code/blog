package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.util.PageBean;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface BlogMapper {

    @Insert("insert into blog (id,title,content,firstPicture,flag,views,appreciation,shareStatement,commentabled,published,recommend,createTime,updateTime,blog_userid,blog_typeid,description) values" +
            " (#{id},#{title},#{content},#{firstPicture},#{flag},#{views},#{appreciation},#{shareStatement},#{commentabled},#{published},#{recommend},#{createTime},#{updateTime},#{blog_userid},#{blog_typeid},#{description}) ")
    void save(Blog blog);

    @Select("select * from blog where id=#{id}")
    @Results(id = "BlogMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "user" ,column="blog_userid",one = @One(select = "xyz.worldzhile.blog.mapper.UserMapper.getUserById",fetchType = FetchType.EAGER)),
            @Result(property = "type" ,column="blog_typeid",one = @One(select = "xyz.worldzhile.blog.mapper.TypeMapper.get",fetchType = FetchType.EAGER)),
            @Result(property = "tags" ,column="id",many = @Many(select = "xyz.worldzhile.blog.mapper.TagMapper.findTagsByBlogId",fetchType = FetchType.LAZY)),
            @Result(property = "comments" ,column="id",many = @Many(select = "xyz.worldzhile.blog.mapper.CommentMapper.gets",fetchType = FetchType.LAZY))
    })
    Blog get(String id);

    @Select("<script>"+"SELECT * FROM blog WHERE 1=1  "
            +"<if test='map!=null'>"
            +"<foreach collection='map.entrySet()' open='' item='value' index='key' close='' > and ${key} like CONCAT('%',#{value},'%') </foreach> "
            +"</if>"
            +"limit #{page.start},#{page.pageCount}"
            +"</script>"
    )
    @ResultMap("BlogMap") //分页的时候不查评论不用提高效率
    List<Blog> list(@Param("page") PageBean<Blog> pageBean,@Param("map") HashMap<String,String> map);

    @Update("update blog set title=#{title},content=#{content},firstPicture=#{firstPicture},flag=#{flag},views=#{views},appreciation=#{appreciation},shareStatement=#{shareStatement}," +
            "commentabled=#{commentabled},published=#{published},recommend=#{recommend}," +
            "updateTime=#{updateTime},blog_typeid=#{blog_typeid},description=#{description}"+
            " where id=#{id}")
    void update(Blog blog);

    @Delete("delete from blog where id=#{id}")
    void deleteBlog(String id);


    @Select("<script>"+"SELECT count(id) FROM blog WHERE 1=1 "
            //+"<if test='map.title!=null'> and title like CONCAT('%',#{map.title},'%')</if>"
            //+"<if test='map.content!=null'> and content like CONCAT('%',#{map.content},'%')</if>"
            +"<if test='map!=null'>"
            +"<foreach collection='map.entrySet()' open='' item='value' index='key' close='' > and ${key} like CONCAT('%',#{value},'%') </foreach> "
            +"</if>"
            +"</script>"
    )
    Integer count(@Param("map") HashMap<String,String> map);


    @Select("select * from blog where title=#{title}")
    @ResultMap("BlogMap")
    Blog getBlogByTitle(String title);


    @Select("select * from blog where published=1 and recommend=1  order by updateTime desc limit 0,#{newBlogShowCount} ")
    List<Blog> indexShow(int newBlogShowCount);


    @Select("<script>"+"SELECT count(id) FROM blog WHERE 1=1"+
            "<if test='query!=null'> and title like CONCAT('%',#{query},'%')</if>"  +
            "<if test='query!=null'> or content like CONCAT('%',#{query},'%')</if>"
            +"</script>"
    )
    Integer searchCount(String query);

    @Select("<script>"+"SELECT * FROM blog WHERE 1=1"+
            "<if test='query!=null'> and title like CONCAT('%',#{query},'%')</if>"  +
            "<if test='query!=null'> or content like CONCAT('%',#{query},'%')</if>"+
            "limit #{page.start},#{page.pageCount}"
            +"</script>"
    )
    @ResultMap("BlogMap")
    List<Blog> searchList(@Param("page") PageBean<Blog> page, @Param("query") String query);

    @Update("update blog set views=#{count} where id=#{id}")
    void view(@Param("count") Integer count,@Param("id") String id);

    @Select("select count(id) from blog where blog_typeid=#{typeid}")
    Integer countByType(String typeid);

    @Select("select blog.* from tag "
            + " left join blogtag "
            + " on tag.id=blogtag.tagid "
            + " left join blog "
            + "on blog.id=blogtag.blogid "
            + " where tagid=#{tagid} "
           +  " limit #{page.start},#{page.pageCount}")
    @ResultMap("BlogMap")
    List<Blog> allWithTagId(@Param("tagid")String tagid,@Param("page") PageBean<Blog> pageBean);

    @Select("select count(*) from tag " +
            "left join blogtag " +
            "on tag.id=blogtag.tagid " +
            "left join blog " +
            "on blog.id=blogtag.blogid " +
            "where tagid=#{tagid}")
    Integer countWithTagId(String tagid);


    @Select("select   DATE_FORMAT(createTime,'%Y') year  from blog GROUP BY year ORDER BY year desc")
    List<String> getYears();

    @Select("select * from blog where DATE_FORMAT(createTime,'%Y') = #{year} ORDER BY createTime desc ")
    List<Blog> getBlogsByYear(String year);

    @Select("select * from blog where published=1 and recommend=1 order  by views desc limit 0, #{hotBlogShowCount}")
    List<Blog> getHot(Integer hotBlogShowCount);
}
