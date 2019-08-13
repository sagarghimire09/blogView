package com.edu.mum.service.impl;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import com.edu.mum.repository.ReviewRepository;
import com.edu.mum.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllReviewsByPost(Post post) {
        return reviewRepository.findAllByPost(post);
    }

    @Override
    public Optional<Review> findByUserAndPost(User usr, Post post) {
        return reviewRepository.findByUserAndPost(usr,post);
    }
}
