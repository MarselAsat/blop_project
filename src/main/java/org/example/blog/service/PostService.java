package org.example.blog.service;

import org.example.blog.dto.PostDto;
import org.example.blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post create(PostDto postDto);
    void delete (long postId);

    Post findById(long postId);

    Post update(long postId, PostDto postDto);

    List<Post> findAll(Optional<String> query);
}
