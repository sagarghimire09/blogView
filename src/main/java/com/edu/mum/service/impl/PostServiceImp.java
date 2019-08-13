package com.edu.mum.service.impl;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.repository.PostRepository;
import com.edu.mum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        // Create our own query
        return this.postRepository.findLates5Posts( PageRequest.of(0,5) );
        // Using Streams also worked Descending order
        //return this.postRepository.findAll( PageRequest.of(0, 2) ).stream().sorted( (a,b) -> a.getDate().compareTo(b.getDate()) ).collect(Collectors.toList());
    }
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post edit(Post post) {
        return null;
    }

    @Override
    public Post create(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {

    }
    //business logic to calculate earning
    //simple logic here just to show how it works
    @Override
    public double getEarningByPost(Long id) {
        double earning = 0.0;
        Optional<Post> post = postRepository.findById(id);
        int ratedCount  = post.get().getRatedCount();
        double averageRating = post.get().getAvgRating();
        if(ratedCount > 2 && averageRating >2){
            earning = 1;
        }
        else if(ratedCount >5 && averageRating >4){
            earning = 5;
        }
        else if(ratedCount >10 && averageRating >=5){
            earning = 10;
        }
        return  earning;
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }


    @Override
    public Page<Post> findByUserOrderedByDatePageable(User user, int page) {
        return postRepository.findByUserOrderByCreateDateDesc(user, new PageRequest(subtractPageByOne(page), 5));
    }

    @Override
    public Page<Post> findAllOrderedByDatePageable(int page) {
        return postRepository.findAllByOrderByCreateDateDesc(new PageRequest(subtractPageByOne(page), 5));
    }


    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
