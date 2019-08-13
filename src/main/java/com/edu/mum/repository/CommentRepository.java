package com.edu.mum.repository;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //    @Query(value="SELECT c.* FROM comments c ORDER BY c.create_date DESC where c.user", nativeQuery = true)
//    List<Comment> findLates5Comments();
    List<Comment> findFirst5ByPost(Post post);
}
