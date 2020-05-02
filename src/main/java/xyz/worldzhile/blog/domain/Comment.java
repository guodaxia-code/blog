package xyz.worldzhile.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
//    管理员评论字段 我的评论
    private boolean my;




    private String blogid;
    private Blog blog;
    private String parentCommentid;
    private Comment parentComment;
    private List<Comment> replyComments;



}
