package xyz.worldzhile.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("index")
    public String toIndex(){
        return "index";
    }

    @GetMapping("blog")
    public String toBlog(){
        return "blog";
    }
    @GetMapping("types")
    public String toTypes(){
        return "types";
    }
    @GetMapping("tags")
    public String toTags(){
        return "tags";
    }
    @GetMapping("archives")
    public String toArchives(){
        return "archives";
    }
    @GetMapping("about")
    public String toAbout(){
        return "about";
    }











}
