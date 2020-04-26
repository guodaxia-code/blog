package xyz.worldzhile.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private String id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;
    private String description;



    private String blog_typeid;
    private String blog_userid;


    private Type type;
    private List<Tag> tags;
    private User user;
    private List<Comment> comments;


    public String getTagids(){
       if (tags==null){
           return null;
       }
        StringBuilder values = new StringBuilder();

        for (Tag tag : tags) {
            values.append(tag.getId()+",");
        }

        return values.length()!=0? values.toString().substring(0,values.length()-1):null;

    }



}
