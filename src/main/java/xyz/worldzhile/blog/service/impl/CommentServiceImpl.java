package xyz.worldzhile.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.worldzhile.blog.domain.Comment;
import xyz.worldzhile.blog.mapper.CommentMapper;
import xyz.worldzhile.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment saveComment(Comment comment) {
        commentMapper.save(comment);
        return   commentMapper.get(comment.getId());
    }
}
