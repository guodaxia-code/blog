package xyz.worldzhile.blog.service;

import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Tag;
import xyz.worldzhile.blog.domain.Type;
import xyz.worldzhile.blog.util.PageBean;

import java.util.HashMap;
import java.util.List;


public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(String id);

    Tag getTagByName(String name);

    List<Tag> getAll();

    List<Tag> listTag(PageBean<Tag> page);


    Tag updateTag(Tag tag);

    void deleteTag(String id);


    List<Tag> indexShow(int tagShowCount);

    HashMap getAllWithTagID(String tagid, PageBean page);
}
