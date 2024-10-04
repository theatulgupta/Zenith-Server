package com.agkminds.zenith.services.Comment;

import com.agkminds.zenith.exceptions.CommentException;
import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UnauthorizedException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Comment;
import com.agkminds.zenith.models.Post;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.CommentRepository;
import com.agkminds.zenith.repository.PostRepository;
import com.agkminds.zenith.services.Post.PostService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment addComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        if (user == null || post == null) {
            throw new UserException("User or post not found");
        }

        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment, Integer commentId, Integer userId) throws UserException, CommentException {
        Comment existingComment = getCommentById(commentId);
        if (existingComment == null) {
            throw new CommentException("Comment not found with commentId " + commentId);
        }

        if (!existingComment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("User is not authorized to edit this comment");
        }
        existingComment.setContent(comment.getContent());
        existingComment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(existingComment);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        Comment comment = getCommentById(commentId);
        User user = userService.findUserById(userId);

        if (user == null) {
            throw new UserException("User not found with userId " + userId);
        }

        if (!comment.getLikes().contains(user)) {
            comment.getLikes().add(user);
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Integer commentId) throws CommentException {
        return commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found with commentId " + commentId));
    }
}