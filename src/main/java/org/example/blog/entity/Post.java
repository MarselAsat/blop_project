package org.example.blog.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Long postId;

    String title;
    String content;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "dt_created")
    LocalDateTime dtCreated;
    @ManyToMany
    private Set<Tag> tags;
    @OneToMany
    private List<Comment> comments;

    LocalDateTime dtUpdated;


    public Post() {

    }
}
