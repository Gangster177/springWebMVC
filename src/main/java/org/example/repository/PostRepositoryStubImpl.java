package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> posts;
    private final AtomicLong postID;

    public PostRepositoryStubImpl() {
        posts = new ConcurrentHashMap<>();
        postID = new AtomicLong(0);
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = postID.incrementAndGet();
            post.setId(id);
            posts.put(id, post);
        } else if (post.getId() != 0) {
            Long currentId = post.getId();
            posts.put(currentId, post);
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Введен неверный id");
        }
    }
}