package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.BlogTag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.mapper.BlogMapper;
import xyz.worldzhile.blog.mapper.BlogTagMapper;
import xyz.worldzhile.blog.mapper.TypeMapper;
import xyz.worldzhile.blog.service.BlogService;
import xyz.worldzhile.blog.service.TypeService;
import xyz.worldzhile.blog.util.PageBean;
import xyz.worldzhile.blog.util.UuidUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;


    @Override
    public Blog saveBlog(Blog blog,String[] tagids) {
         blog.setViews(0);
         blog.setCreateTime(new Date());
         blog.setUpdateTime(new Date());
        String id = blog.getId();
        blogMapper.save(blog);
        //flag  tags
        if (tagids!=null){
            for (String tagid : tagids) {
                blogTagMapper.save( new BlogTag(UuidUtil.getUuid(), id, tagid));
            }
        }


         return blogMapper.get(blog.getId());
    }

    @Override
    public Blog getBlog(String id) {
        return blogMapper.get(id);
    }

    @Override
    public List<Blog> listBlog(PageBean<Blog> page, HashMap<String,String> map) {
        Integer count = blogMapper.count(map);
        page.setTotalCount(count);
        List<Blog> list = blogMapper.list(page,map);
        return list;
    }

    @Override
    public Blog updateBlog(Blog blog,String[] tagids) {
        String id = blog.getId();
        blog.setViews(blogMapper.get(id).getViews());
        blogTagMapper.deleteBlogTag(id);
        for (String tagid : tagids) {
            blogTagMapper.save(new BlogTag(UuidUtil.getUuid(),id,tagid));
        }
        blog.setUpdateTime(new Date());
         blogMapper.update(blog);

        return blogMapper.get(blog.getId());
    }

    @Override
    public void deleteBlog(String id) {

        blogTagMapper.deleteBlogTag(id);
        blogMapper.deleteBlog(id);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogMapper.getBlogByTitle(title);
    }

    @Override
    public List<Blog> indexShow(int newBlogShowCount) {
        return blogMapper.indexShow(newBlogShowCount);
    }

    @Override
    public List<Blog> search(PageBean<Blog> page, String query) {
        Integer count = blogMapper.searchCount(query);
        page.setTotalCount(count);
        List<Blog> list = blogMapper.searchList(page,query);
        return list;
    }
}
