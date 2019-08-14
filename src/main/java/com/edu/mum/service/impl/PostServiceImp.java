package com.edu.mum.service.impl;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import com.edu.mum.repository.PostRepository;
import com.edu.mum.repository.ReviewRepository;
import com.edu.mum.service.PostService;
import com.edu.mum.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository, ReviewRepository reviewRepository) {
        this.postRepository = postRepository;
        this.reviewRepository = reviewRepository;
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
        List<Review> reviewList = reviewRepository.findAllByPost(post.get());
        int count = reviewList.size();
        double avgRating = ArithmeticUtils.getAvgRating(reviewList);

        if(avgRating >2 && count>2){
            earning = 1;
        }
        else if(avgRating >3 && count>3){
            earning = 2;
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
    public Page<Post> findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(String title, String authorFirstName, int page) {
        return postRepository.findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(title,authorFirstName, new PageRequest(subtractPageByOne(page), 5));
    }


    @Override
    public Page<Post> findAllOrderedByDatePageable(int page) {
        return postRepository.findAllByOrderByCreateDateDesc(new PageRequest(subtractPageByOne(page), 5));
    }


    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }


	@Override
	public int getTotalPostCount(boolean status) {
		if(status) {
			return postRepository.countByStatus(true);
		}else {
			return postRepository.countByStatus(false);
		}
	}


	@Override
	public int getClaimedPostCount() {
		return postRepository.countByClaimedStatus(true);
	}


	@Override
	public int getPostCountForUser(User user, boolean status) {
		return postRepository.countByUserAndStatus(user, status);
	}


	@Override
	public int getClaimedPostCountByUser(User user) {
		return postRepository.countByUserAndClaimedStatus(user, true);
	}

}
