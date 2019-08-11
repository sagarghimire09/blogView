package com.edu.mum.service;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {


    Post create(Post post);

    /**
     * Finds a {@link Page ) of {@link Post} of provided user ordered by date
     */
    Page<Post> findByUserOrderedByDatePageable(User user, int page);

    /**
     * Finds a {@link Page ) of all {@link Post} ordered by date
     */
    Page<Post> findAllOrderedByDatePageable(int page);
//    Page<Post> findAll(Pageable pageable);

    List<Post> findLatest5();
    void delete(Post post);

    List<Post> findAll();

    //    List<Post> findLatest5();
    Optional<Post> findById(Long id);

    //    Post create(Post post);
    Post edit(Post post);

    void deleteById(Long id);
}
