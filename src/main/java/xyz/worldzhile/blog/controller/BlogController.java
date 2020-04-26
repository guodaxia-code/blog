package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.domain.User;
import xyz.worldzhile.blog.service.BlogService;
import xyz.worldzhile.blog.service.TagService;
import xyz.worldzhile.blog.service.TypeService;
import xyz.worldzhile.blog.util.MapUrl;
import xyz.worldzhile.blog.util.PageBean;
import xyz.worldzhile.blog.util.UuidUtil;


import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "blogs/{page}/{pageCount}",method = {RequestMethod.GET,RequestMethod.POST})
    public String getBlogs(@PathVariable Integer page, @PathVariable Integer pageCount, @RequestParam() Map<String,String> cons , Model model){

        if (cons!=null||cons.containsKey("message")){
            cons.remove("message");
        }

        PageBean<Blog> blogPageBean = new PageBean<Blog>(page,pageCount);

        List<Blog> blogs = blogService.listBlog(blogPageBean, (HashMap<String, String>) cons);
        blogPageBean.setList(blogs);
        List<Type> types = typeService.getAll();


        model.addAttribute("types",types);
        model.addAttribute("page",blogPageBean);
        String conditions = MapUrl.asUrlParams(cons);
        model.addAttribute("conditions",conditions);
        model.addAttribute("consAfter",cons);
        return  "admin/blogs";
    }




    @GetMapping("blog-input")
    public String getBloginput(Model model){
        List<Type> types = typeService.getAll();
        List<Tag> tags = tagService.getAll();


        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        return  "admin/blog-input";
    }


    @PostMapping("blog-input")
    public String saveBloginput(Blog blog, @RequestParam String[] tagids, Model model,RedirectAttributes redirectAttributes, HttpSession session){
        blog.setId(UuidUtil.getUuid());
//        User user = (User) session.getAttribute("user");
//        blog.setBlog_userid(user.getId());
        blog.setBlog_userid("000");
        Blog blog1 = blogService.saveBlog(blog, tagids);
        if (blog1!=null){
            redirectAttributes.addAttribute("message","博客新增成功");
        }else {
            redirectAttributes.addAttribute("message","博客新增失败");
        }

        return  "redirect:/admin/blogs/1/6";
    }



    @GetMapping("blog-editor/{id}")
    public String getBlogditor(@PathVariable String id,Model model){
        Blog blog = blogService.getBlog(id);
        List<Type> types = typeService.getAll();
        List<Tag> tags = tagService.getAll();


        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("blog",blog);
        return  "admin/blog-input";
    }


    @PostMapping("{id}/blog-editor")
    public String getTageditor(Blog blog, @PathVariable String id,@RequestParam String[] tagids, Model model,RedirectAttributes redirectAttributes){

        Blog blog1 = blogService.updateBlog(blog, tagids);
        if (blog1!=null){
            redirectAttributes.addAttribute("message","博客修改成功");
        }else {
            redirectAttributes.addAttribute("message","博客修改失败");
        }

        return  "redirect:/admin/blogs/1/6";
    }


    @GetMapping("{id}/blog-delete")
    public String getTagdelete(@PathVariable String id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addAttribute("message","博客删除成功");
        return  "redirect:/admin/blogs/1/6";
    }

}
