package xyz.worldzhile.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("index")
    public String getIndex(){
        return  "admin/index";
    }
}
