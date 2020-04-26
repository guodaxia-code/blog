package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.mapper.TagMapper;
import xyz.worldzhile.blog.mapper.TypeMapper;
import xyz.worldzhile.blog.service.TagService;
import xyz.worldzhile.blog.util.PageBean;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

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


}
