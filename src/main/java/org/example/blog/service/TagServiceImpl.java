package org.example.blog.service;

import org.example.blog.entity.Tag;
import org.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void create(String name) {
        Tag tag = new Tag(name);
        tagRepository.save(tag);
    }

    @Override
    public void create(String... names) {
        Arrays.stream(names).forEach(this::create);
    }

    @Override
    public Tag findByName(String name) {
        Tag tag = tagRepository.findByName(name).orElseThrow();
        tag.getPosts().size();
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> allTags = tagRepository.findAll();
        allTags.forEach(t -> t.getPosts().size());
        return allTags;
    }

    @Override
    public Map<String, Integer> counterPostTag() {
        Map<String, Integer> tagPostsCounter = tagRepository.findAll().stream()
                .collect(Collectors.toMap(Tag::getName, tag -> tag.getPosts().size()));
        return tagPostsCounter;
    }
}
