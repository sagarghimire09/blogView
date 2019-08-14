package com.edu.mum.service;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostService {


    Post create(Post post);

    Page<Post> findByUserOrderedByDatePageable(User user, int page);
    Page<Post> findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(String title, String authorFirstName, int page);

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

    double getEarningByPost(Long id);

	int getTotalPostCount(boolean status);

	int getClaimedPostCount();

	int getPostCountForUser(User user, boolean status);

	int getClaimedPostCountByUser(User user);
}
