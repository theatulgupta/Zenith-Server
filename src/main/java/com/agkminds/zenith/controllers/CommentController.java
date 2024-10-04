package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.CommentException;
import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Comment;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Comment.CommentService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    private User getUserFromJwt(String jwt) throws UserException {
        return userService.findUserByJwt(jwt);
    }

    @PostMapping("/post/{postId}")
    public Comment addComment(@RequestBody Comment comment, @PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws UserException, PostException {
        return commentService.addComment(comment, postId, getUserFromJwt(jwt).getId());
    }

    @PutMapping("/like/{commentId}")
    public Comment likeComment(@PathVariable Integer commentId, @RequestHeader("Authorization") String jwt) throws UserException, CommentException {
        return commentService.likeComment(commentId, getUserFromJwt(jwt).getId());
    }

    @PutMapping("/update/{commentId}")
    public Comment updateComment(@PathVariable Integer commentId, @RequestBody Comment comment, @RequestHeader("Authorization") String jwt) throws UserException, CommentException {
        return commentService.updateComment(comment, commentId, getUserFromJwt(jwt).getId());
    }
}