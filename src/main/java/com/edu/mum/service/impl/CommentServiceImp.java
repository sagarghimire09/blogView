package com.edu.mum.service.impl;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.repository.CommentRepository;
import com.edu.mum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public List<Comment> findFirst5ByPost(Post post) {
        return commentRepository.findFirst5ByPost(post);
    }

	@Override
	public int getCommentCountForUser(User user) {
		int res = 0;
		Collection<Post> posts = user.getPosts();
		for(Post p:posts) {
			res += p.getComments().size();
		}
		return res;
	}
}
