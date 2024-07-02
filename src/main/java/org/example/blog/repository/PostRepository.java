package org.example.blog.repository;

import org.example.blog.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    List<Post> findByContentContainingIgnoreCase(String content, Sort sort);
}
