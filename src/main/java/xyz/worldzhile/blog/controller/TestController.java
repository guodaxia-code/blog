package xyz.worldzhile.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.worldzhile.blog.domain.*;
import xyz.worldzhile.blog.mapper.CommentMapper;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

//    @RequestMapping("test/{id}")
//    public  String test(@PathVariable Integer id){
//        System.out.println(id);
//        return "testmusic";
//    }

    @RequestMapping("test")
    public  String test(){

        return "testmusic";
    }


}
