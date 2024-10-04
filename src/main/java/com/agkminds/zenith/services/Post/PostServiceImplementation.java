package com.agkminds.zenith.services.Post;

import com.agkminds.zenith.exceptions.PostException;
import com.agkminds.zenith.exceptions.UnauthorizedException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Post;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.PostRepository;
import com.agkminds.zenith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId) throws PostException, UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id " + userId));

        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws PostException, UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id " + postId));

        if (!post.getUser().equals(user)) {
            throw new UnauthorizedException("You are not authorized to delete this post.");
        }

        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws PostException, UserException {
        if (!userRepository.existsById(userId)) {
            throw new UserException("User not found with id " + userId);
        }
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id " + postId));
    }

    @Override
    public List<Post> findAllPost() throws PostException {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new PostException("No posts found");
        }
        return posts;
    }

    @Override
    public Post toggleSavePost(Integer postId, Integer userId) throws PostException, UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with id " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id " + postId));

        if (user.getSavedPosts().contains(post)) {
            user.getSavedPosts().remove(post);
        } else {
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with id " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id " + postId));

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user);
        } else {
            post.getLikedBy().add(user);
        }
        postRepository.save(post);
        return post;
    }
}