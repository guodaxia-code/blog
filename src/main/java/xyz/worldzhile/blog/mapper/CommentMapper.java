package xyz.worldzhile.blog.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import xyz.worldzhile.blog.domain.Comment;
import xyz.worldzhile.blog.domain.Tag;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Insert("insert into comment (id,nickname,email,content,createTime,blogid,parentCommentid,my,avatar) values (#{id},#{nickname},#{email},#{content},#{createTime},#{blogid},#{parentCommentid},#{my},#{avatar}) ")
    void save(Comment comment);

    @Select("select * from comment where id=#{id}")
    @Results( id = "father",value = {
            @Result(id = true,property = "id",column = "id"),
            // 父
            @Result(property = "parentComment" ,column="parentCommentid",one = @One(select = "xyz.worldzhile.blog.mapper.CommentMapper.get",fetchType = FetchType.EAGER))
    })
    Comment get(String id);

    //一级评论
    @Select("select * from comment where blogid=#{blogid} and parentCommentid is null order by createTime")
    @Results(id = "CommentMap",value = {
            @Result(id = true,property = "id",column = "id"),
            //子评论自己递归放入了
            //@Result(property = "replyComments" ,column="id",many = @Many(select = "xyz.worldzhile.blog.mapper.CommentMapper.getsons",fetchType = FetchType.LAZY))
    })
    List<Comment> gets(String blogid);

    //二级评论
    @Select("select * from comment where parentCommentid =#{paraentId} order by createTime")
    @Results( id = "fatherOther",value = {
            @Result(id = true,property = "id",column = "id"),
            //父评论自己递归放入了
            //@Result(property = "parentComment" ,column="parentCommentid",one = @One(select = "xyz.worldzhile.blog.mapper.CommentMapper.get",fetchType = FetchType.LAZY))
    })
    List<Comment> getsons(@Param("paraentId") String paraentId);

}
