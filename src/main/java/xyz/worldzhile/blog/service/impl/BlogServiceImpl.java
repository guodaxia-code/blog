package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.BlogTag;
import xyz.worldzhile.blog.domain.Comment;
import xyz.worldzhile.blog.mapper.BlogMapper;
import xyz.worldzhile.blog.mapper.BlogTagMapper;
import xyz.worldzhile.blog.mapper.CommentMapper;
import xyz.worldzhile.blog.service.BlogService;
import xyz.worldzhile.blog.util.PageBean;
import xyz.worldzhile.blog.util.UuidUtil;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private CommentMapper commentMapper;

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

    /**
     * 递归 把孙子一下的作为二级评论
     * @param id
     * @return
     */
    @Override
    public Blog getBlog(String id) {
        List<Comment> gets = commentMapper.gets(id);

        for (Comment get : gets) {
            List <Comment> list=new ArrayList<Comment>();
            digui(get,list);
            get.setReplyComments(list);
        }

        Blog blog = blogMapper.get(id);
        blog.setComments(gets);
        return blog;

    }

    /*
        递归
     */
    void digui(Comment comment,List <Comment> list){
        List<Comment> gets = commentMapper.getsons(comment.getId());
        if (gets==null||gets.size()==0)
            return;
        list.addAll(gets);
        for (Comment get : gets) {
            //父评论装进去
            get.setParentComment(comment);
            digui(get,list);
        }
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

    @Override
    public void viewCount(int count,String id) {
        blogMapper.view(count,id);
    }

    @Override
    public Integer getCountByType(String typeid) {
        return blogMapper.countByType(typeid);
    }

    @Override
    public Map<String, List<Blog>> archives() {
        HashMap<String, List<Blog>> result = new HashMap<>();
        List<String> years=blogMapper.getYears();
        for (String year : years) {
            List<Blog> list=  blogMapper.getBlogsByYear(year);
            result.put(year,list);
        }
        return result;
    }

    @Override
    public Integer getCount() {
        return blogMapper.count(null);
    }

    @Override
    public List<Blog> getHotBlogs(Integer hotBlogShowCount) {
        return blogMapper.getHot(hotBlogShowCount);
    }


}
