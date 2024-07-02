package org.example.blog.controller;

import org.example.blog.dto.CommentDto;
import org.example.blog.entity.Comment;
import org.example.blog.repository.CommentRepository;
import org.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentRepository commentRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    public ResponseEntity<List<CommentDto>> findAll(){
        return ResponseEntity.ok(commentRepository.findAll()
                .stream()
                .map(this::fromComment)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<CommentDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                fromComment(commentRepository.findById(id).orElseThrow()));
    }

    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fromComment(commentService.create(commentDto)));
    }

    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(fromComment(commentService.update(id,commentDto)));
    }

    public void delete(@PathVariable Long id){
        commentService.delete(id);
    }
    private CommentDto fromComment(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getCommentId());
        commentDto.setContent(comment.getContent());
        //commentDto.setUser(userService.getUsername(comment.getUserId()));
        commentDto.setDtCreated(comment.getDtCreated());
        commentDto.setDtUpdated(comment.getDtUpdate());
        commentDto.setPostId(comment.getPost().getPostId());

        return commentDto;
    }
}
