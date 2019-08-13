package com.edu.mum.service;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);
    List<Comment> findFirst5ByPost(Post post);
}
