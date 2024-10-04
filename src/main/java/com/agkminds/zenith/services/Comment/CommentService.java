package com.agkminds.zenith.services.Comment;

import com.agkminds.zenith.exceptions.CommentException;
import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Comment;

public interface CommentService {
    Comment addComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;

    Comment updateComment(Comment comment, Integer commentId, Integer userId) throws UserException, CommentException;

    Comment likeComment(Integer commentId, Integer userId) throws UserException, CommentException;

    Comment getCommentById(Integer commentId) throws UserException, CommentException;
}