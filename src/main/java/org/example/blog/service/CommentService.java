package org.example.blog.service;

import org.example.blog.dto.CommentDto;
import org.example.blog.entity.Comment;
import java.util.List;

public interface CommentService {

    void create(Long postId, String comment);

    List<Comment> findAll();

    Comment create(CommentDto commentDto);

    Comment update(Long id, CommentDto commentDto);

    Comment findById(Long id);

    void delete(Long id);
}
