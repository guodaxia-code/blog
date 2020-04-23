package xyz.worldzhile.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class TypeController {

    @GetMapping("types")
    public String getTypes(){
        return  "admin/types";
    }

    @GetMapping("type-input")
    public String getTypeinput(){
        return  "admin/type-input";
    }
}
