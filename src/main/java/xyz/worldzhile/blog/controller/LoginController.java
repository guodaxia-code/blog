package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.worldzhile.blog.domain.User;
import xyz.worldzhile.blog.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String getLogintoo(@RequestParam(value = "language",required = false) String language,Model model){
        if (language!=null) {
            if (language.equals("zh_CN")){
                model.addAttribute("flag",1);
            }else if (language.equals("en_US")){
                model.addAttribute("flag",0);
            }
        }

        return  "admin/login";
    }

    @PostMapping("login")
    public String getLoginPost(User user, HttpSession session, Model model){
        User getUser = userService.checkUser(user.getUsername(), user.getPassword());
        if (getUser!=null){
            session.setAttribute("user",getUser);
            return "redirect:/admin/index";
        }else {
            model.addAttribute("message","用户名或者密码错误");
            return "admin/login";
        }
    }

    @GetMapping("logout")
    public String getLogout(HttpSession session){
        session.removeAttribute("user");
        return  "redirect:/admin/login";
    }
}
