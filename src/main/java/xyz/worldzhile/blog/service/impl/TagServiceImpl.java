package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Blog;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.mapper.BlogMapper;
import xyz.worldzhile.blog.mapper.TagMapper;
import xyz.worldzhile.blog.mapper.TypeMapper;
import xyz.worldzhile.blog.service.TagService;
import xyz.worldzhile.blog.util.PageBean;

import java.util.HashMap;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Tag saveTag(Tag tag) {
         tagMapper.save(tag);
         return tagMapper.get(tag.getId());
    }

    @Override
    public Tag getTag(String id) {
        return tagMapper.get(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getAll() {
        return tagMapper.all();
    }

    @Override
    public List<Tag> listTag(PageBean<Tag> page) {
        Integer count = tagMapper.count();
        page.setTotalCount(count);
        return tagMapper.list(page);
    }

    @Override
    public Tag updateTag(Tag tag) {
         tagMapper.update(tag);
         return tagMapper.get(tag.getId());
    }

    @Override
    public void deleteTag(String id) {
        tagMapper.deleteTag(id);
    }

    @Override
    public List<Tag> indexShow(int tagShowCount) {
        return tagMapper.indexShow(tagShowCount);
    }

    @Override
    public HashMap<String, Object> getAllWithTagID(String tagid, PageBean page) {
        List<Tag> tags = tagMapper.all();
        if (tagid.equals("firstValue")){
            if (tags.get(0)!=null){
                tagid=tags.get(0).getId();
            }
        }
        for (Tag tag : tags) {
            Integer total = blogMapper.countWithTagId(tag.getId());
            List<Blog> blogs = blogMapper.allWithTagId(tag.getId(),page);
            tag.setCount(blogs.size());
            if (tag.getId().equals(tagid)){
                page.setTotalCount(total);
                page.setList(blogs);
                tag.setBlogs(blogs);
            }
        }

        HashMap<String, Object> result = new HashMap<>(3,1);
        result.put("tagid",tagid);
        result.put("tags",tags);
        return result;
    }


}
