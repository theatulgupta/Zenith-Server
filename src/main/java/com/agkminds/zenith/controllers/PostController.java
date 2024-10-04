package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Post;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Post.PostService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        return postService.createPost(post, getUserFromJwt(jwt).getId());
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        User user = getUserFromJwt(jwt);
        return postService.deletePost(postId, user.getId());
    }

    @GetMapping("/user")
    public List<Post> findPostByUserId(@RequestHeader("Authorization") String jwt) throws PostException, UserException {
        return postService.findPostByUserId(getUserFromJwt(jwt).getId());
    }

    @GetMapping("/{postId}")
    public Post findPostById(@PathVariable("postId") Integer postId) throws PostException {
        return postService.findPostById(postId);
    }

    @GetMapping
    public List<Post> findAllPost() throws PostException {
        return postService.findAllPost();
    }

    @PutMapping("/{postId}")
    public Post toggleSavePost(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        return postService.toggleSavePost(postId, getUserFromJwt(jwt).getId());
    }

    @PutMapping("/like/{postId}")
    public Post likePost(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {
        return postService.likePost(postId, getUserFromJwt(jwt).getId());
    }

    private User getUserFromJwt(String jwt) throws UserException {
        return userService.findUserByJwt(jwt);
    }
}