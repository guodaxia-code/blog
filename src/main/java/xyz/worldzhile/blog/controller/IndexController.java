package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.exception.NotFindException;
import xyz.worldzhile.blog.service.BlogService;
import xyz.worldzhile.blog.service.TagService;
import xyz.worldzhile.blog.service.TypeService;
import xyz.worldzhile.blog.util.MarkDownUtil;
import xyz.worldzhile.blog.util.PageBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private static final Integer TYPE_SHOW_COUNT=6;

    private static final Integer TAG_SHOW_COUNT=10;

    private static final Integer NEW_BLOG_SHOW_COUNT=8;

    private static final Integer HOT_BLOG_SHOW_COUNT=3;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("index/{page}/{pageCount}")
    public String toIndex(@PathVariable Integer page, @PathVariable Integer pageCount, Model model){
        PageBean<Blog> blogPageBean = new PageBean<>(page,pageCount);
        List<Blog> blogs = blogService.listBlog(blogPageBean, null);
        blogPageBean.setList(blogs);
        List<Type> types = typeService.indexShow(TYPE_SHOW_COUNT);
        List<Tag> tags = tagService.indexShow(TAG_SHOW_COUNT);
        List<Blog> news = blogService.indexShow(NEW_BLOG_SHOW_COUNT);


        model.addAttribute("page",blogPageBean);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("news",news);
        return "index";
    }

    @GetMapping("blog/{id}")
    public String toBlog(@PathVariable String id, Model model, RedirectAttributes redirectAttributes){
        Blog blog = null;
        try {
             blog = blogService.getBlog(id);
        }catch (Exception e){
            throw new NotFindException("找不到博客");
        }

        blog.setViews(blog.getViews()+1);
        blogService.viewCount(blog.getViews(),blog.getId());
        String afterHtml = MarkDownUtil.markDownContentToHtml(blog.getContent());
        blog.setContent(afterHtml);
        model.addAttribute("blog",blog);
        return "blog";
    }


    @GetMapping("types/{page}/{pageCount}")
    public String toTypes(Model model,@RequestParam(required = false,defaultValue = "firstValue") String typeid,@PathVariable Integer page,@PathVariable Integer pageCount){
        List<Type> types = typeService.getAll();
        for (Type type : types) {
            type.setCount(blogService.getCountByType(type.getId()));
        }
        PageBean<Blog> pageBean = new PageBean<>(page, pageCount);

        if (typeid.equals("firstValue")){
            for (Type type : types) {
                if (type.getCount()!=0) {
                    typeid= type.getId();
                    break;
                }
            }

        }
        HashMap<String, String> map = new HashMap<>();
        map.put("blog_typeid",typeid);
        List<Blog> blogs = blogService.listBlog(pageBean, map);
        pageBean.setList(blogs);
        model.addAttribute("page",pageBean);
        model.addAttribute("types",types);
        model.addAttribute("typeid",typeid);
        return "types";
    }



    @GetMapping("tags/{page}/{pageCount}")
    public String toTags(Model model,@RequestParam(required = false,defaultValue = "firstValue") String tagid,@PathVariable Integer page,@PathVariable Integer pageCount){

        PageBean<Blog> pageBean = new PageBean<>(page, pageCount);
        HashMap resultMap = tagService.getAllWithTagID(tagid, pageBean);
        model.addAttribute("tags",resultMap.get("tags"));
        model.addAttribute("tagid",resultMap.get("tagid"));
        model.addAttribute("page",pageBean);

        return "tags";
    }


    @GetMapping("archives")
    public String toArchives(Model model){

        Map<String, List<Blog>> archives = blogService.archives();
        model.addAttribute("archives",archives);
        model.addAttribute("count",blogService.getCount());
        return "archives";
    }


    @GetMapping("about")
    public String toAbout(){
        return "about";
    }


    @PostMapping("search/{page}/{pageCount}")
    public String toSearch(Model model, @RequestParam(defaultValue = "") String query,@PathVariable Integer page,@PathVariable Integer pageCount){

        PageBean<Blog> blogPageBean = new PageBean<>(page,pageCount);
        List<Blog> blogs = blogService.search(blogPageBean, query);
        blogPageBean.setList(blogs);

        model.addAttribute("page",blogPageBean);
        model.addAttribute("query",query);
        return "search";
    }


    //最热博客3篇
    @GetMapping("hotBlogs")
    public String toHotBlogs(Model model){

       List<Blog> blogs= blogService.getHotBlogs(HOT_BLOG_SHOW_COUNT);

       model.addAttribute("blogs",blogs);
        return "common::hotBlogList";
    }


    //首页路径
    @GetMapping(value = {"","index"})
    public String toIndex(){
        return "redirect:/index/1/6";
    }



}
