package xyz.worldzhile.blog.service;

import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.util.PageBean;

import java.util.HashMap;
import java.util.List;


public interface BlogService {

    Blog saveBlog(Blog blog,String[] tagids);

    Blog getBlog(String id);


    List<Blog> listBlog(PageBean<Blog> page, HashMap<String,String> map);

    Blog updateBlog(Blog blog,String[] tagids);

    void deleteBlog(String id);

    Blog getBlogByTitle(String title);

    List<Blog> indexShow(int newBlogShowCount);

    List<Blog> search(PageBean<Blog> blogPageBean, String query);
}
