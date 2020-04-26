package xyz.worldzhile.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.worldzhile.blog.domain.*;
import xyz.worldzhile.blog.mapper.BlogMapper;
import xyz.worldzhile.blog.mapper.TagMapper;
import xyz.worldzhile.blog.mapper.UserMapper;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagMapper tagMapper;


    @ResponseBody
    @RequestMapping("test")
    public Blog test(){
        Blog blog = blogMapper.get("92a8d6d99a8b4d85a636eeb95db9e05d");
        System.out.println(blog);
        return blog;
    }



}
