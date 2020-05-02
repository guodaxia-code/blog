package xyz.worldzhile.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MusicController {

    @GetMapping("music")
    public String getMusic(){
        return  "music";
    }
}
