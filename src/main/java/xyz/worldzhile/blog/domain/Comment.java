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


    private Blog blog;
    private Comment parentComment;
    private List<Comment> replyComments;



}
