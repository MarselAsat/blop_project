package org.example.blog.service;

import org.example.blog.dto.PostDto;
import org.example.blog.entity.Post;
import org.example.blog.entity.Tag;
import org.example.blog.repository.PostRepository;
import org.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Post create(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .dtCreated(LocalDateTime.now())
                .tags(parseTag(postDto.getTags()))
                .build();

        return postRepository.save(post);
    }

    @Override
    public void delete(long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        postRepository.delete(post);
    }

    @Override
    public Post findById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.getTags().size();
        post.getComments().size();
        return null;
    }

    @Override
    public Post update(long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow();
        if(StringUtils.hasText(postDto.getContent())){
            post.setContent(postDto.getContent());
        }
        if(StringUtils.hasText(postDto.getTitle())){
            post.setContent(postDto.getTitle());
        }
        if(postDto.getTags() != null){
            Set<Tag> newTags = parseTag(postDto.getTags());
            post.getTags().clear();
            removeUnusedTags(post);
            post.setTags(newTags);
        }
        post.setDtUpdated(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll(Optional<String> query) {
        List<Post> posts = null;
        if (query.isPresent()){
            posts = postRepository.findByContentContainingIgnoreCase(query.get(), Sort.by("dtCreated").descending());
        }else {
            postRepository.findAll(Sort.by("dtCreated").descending());
        }
        posts.forEach(p -> p.getTags().size());
        return posts;
    }

    private Set<Tag> parseTag(String tags) {
        if (!StringUtils.hasText(tags)){
            return new HashSet<>();
        }

        return Arrays.stream(tags.split(" "))
                .filter(StringUtils::hasText)
                .map(tagName -> tagRepository
                        .findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName))))
                .collect(Collectors.toSet());
    }

    private void removeUnusedTags(Post post){
        Set<Tag> unusedTags = post.getTags().stream()
                .filter(t -> t.getPosts().size() >= 1)
                .collect(Collectors.toSet());
        if (unusedTags.size() > 0){
            tagRepository.deleteAll(unusedTags);
        }
    }
}
