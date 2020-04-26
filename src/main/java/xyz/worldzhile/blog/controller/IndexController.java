package xyz.worldzhile.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

import java.util.List;

@Controller
public class IndexController {

    private static final Integer TypeShowCount=6;

    private static final Integer TagShowCount=10;

    private static final Integer NewBlogShowCount=8;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("index/{page}/{pageCount}")
    public String toIndex(@PathVariable Integer page, @PathVariable Integer pageCount, Model model){
        PageBean<Blog> blogPageBean = new PageBean<Blog>(page,pageCount);
        List<Blog> blogs = blogService.listBlog(blogPageBean, null);
        blogPageBean.setList(blogs);
        List<Type> types = typeService.indexShow(TypeShowCount);
        List<Tag> tags = tagService.indexShow(TagShowCount);
        List<Blog> news = blogService.indexShow(NewBlogShowCount);


        model.addAttribute("page",blogPageBean);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("news",news);

        return "index";
    }

    @GetMapping("blog/{id}")
    public String toBlog(@PathVariable String id, Model model, RedirectAttributes redirectAttributes){
        Blog blog = blogService.getBlog(id);
        if (blog==null){
            redirectAttributes.addAttribute("message","找不到博客~~");
            throw new NotFindException("找不到博客");
        }
        String afterHtml = MarkDownUtil.markDownContentToHtml(blog.getContent());
        blog.setContent(afterHtml);
        model.addAttribute("blog",blog);

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


    @PostMapping("search/{page}/{pageCount}")
    public String toSearch(Model model, @RequestParam(defaultValue = "") String query){
        System.out.println(query);
        Integer page=1;
        Integer pageCount=6;
        PageBean<Blog> blogPageBean = new PageBean<Blog>(page,pageCount);
        List<Blog> blogs = blogService.search(blogPageBean, query);
        blogPageBean.setList(blogs);

        model.addAttribute("page",blogPageBean);
        model.addAttribute("query",query);
        return "search";
    }








}
