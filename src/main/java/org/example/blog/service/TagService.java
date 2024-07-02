package org.example.blog.service;

import org.example.blog.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {

    void create(String name);

    void create(String... names);

    Tag findByName(String name);

    List<Tag> findAll();

    Map<String, Integer> counterPostTag();
}
