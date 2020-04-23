package xyz.worldzhile.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class BlogController {

    @GetMapping("blogs")
    public String getBlogs(){
        return  "admin/blogs";
    }

    @GetMapping("blog-input")
    public String getBloginput(){
        return  "admin/blog-input";
    }
}
