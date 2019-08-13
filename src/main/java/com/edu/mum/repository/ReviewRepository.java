package com.edu.mum.repository;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByPost(Post post);
    Optional<Review> findByUserAndPost(User usr, Post post);

}
