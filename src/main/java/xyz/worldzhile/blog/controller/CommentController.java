package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.worldzhile.blog.domain.Comment;
import xyz.worldzhile.blog.domain.User;
import xyz.worldzhile.blog.service.CommentService;
import xyz.worldzhile.blog.util.UuidUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

@Controller
public class CommentController {

    private static String URL="  https://i.picsum.photos/id/100/100/100.jpg";
    private static Random random=new Random();

    public static String getAvatarUrl(){
        int id = random.nextInt(Integer.parseInt("1050"));
        return URL.replaceFirst("100", String.valueOf(id));
    }

    @Autowired
    private CommentService commentService;

    @PostMapping("comment/{id}")
    public String writeComment(@PathVariable String id, Comment comment, RedirectAttributes redirectAttributes, HttpSession session){


        if ("".equals(comment.getParentCommentid())){
            comment.setParentCommentid(null);
        }

        comment.setId(UuidUtil.getUuid());
        comment.setBlogid(id);
        comment.setCreateTime(new Date());


        User user = (User) session.getAttribute("user");
        if (user!=null){
            comment.setMy(true);
            //不用登陆,默认随机图片 id[0,1050]
            comment.setAvatar(user.getAvatar());
        }else {
            comment.setMy(false);
            //不用登陆,默认随机图片 id[0,1050]
            comment.setAvatar(getAvatarUrl());
        }



        Comment comment1 = commentService.saveComment(comment);
        if (comment1!=null){
            redirectAttributes.addAttribute("message","评论成功");
        }else {
            redirectAttributes.addAttribute("message","评论失败");
        }

        return  "redirect:/blog/"+id;
    }



}
