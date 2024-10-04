package com.agkminds.zenith.services.Post;

import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Integer userId) throws PostException, UserException;

    String deletePost(Integer postId, Integer userId) throws PostException, UserException;

    List<Post> findPostByUserId(Integer userId) throws PostException, UserException;

    Post findPostById(Integer postId) throws PostException;

    List<Post> findAllPost() throws PostException;

    Post toggleSavePost(Integer postId, Integer userId) throws PostException, UserException;

    Post likePost(Integer postId, Integer userId) throws PostException, UserException;
}