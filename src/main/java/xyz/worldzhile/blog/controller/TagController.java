package xyz.worldzhile.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class TagController {

    @GetMapping("tags")
    public String getTags(){
        return  "admin/tags";
    }

    @GetMapping("tag-input")
    public String getTypeinput(){
        return  "admin/tag-input";
    }
}
