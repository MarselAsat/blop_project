package org.example.blog.service;

import org.example.blog.dto.CommentDto;
import org.example.blog.entity.Comment;
import org.example.blog.repository.CommentRepository;
import org.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void create(Long postId, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .post(postRepository.findById(postId).orElseThrow())
                .dtCreated(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment create(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .dtCreated(LocalDateTime.now())
                .post(postRepository.findById(commentDto.getPostId()).orElseThrow())
                .build();

        return comment;
    }

    @Override
    public Comment update(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (StringUtils.hasText(commentDto.getContent())){
            comment.setContent(commentDto.getContent());
        }
        comment.setDtUpdate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
    }
}
