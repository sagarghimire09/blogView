package com.edu.mum.repository;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

    Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);

    Optional<Post> findById(Long id);
    @Query(value="SELECT p.* FROM posts p ORDER BY p.create_date DESC", nativeQuery = true)
    List<Post> findLates5Posts(Pageable pageable);
    
	int countByStatus(boolean status);
	int countByClaimedStatus(boolean claimed);
	int countByUserAndStatus(User user, boolean status);
	int countByUserAndClaimedStatus(User user, boolean claimed);
    Page<Post> findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(String title, String firstName, Pageable pageable);
}
