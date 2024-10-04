package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Story;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Story.StoryService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/story")
public class StoryController {

    private final StoryService storyService;
    private final UserService userService;

    @Autowired
    public StoryController(StoryService storyService, UserService userService) {
        this.storyService = storyService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return storyService.createStory(story, user);
    }

    @GetMapping("/user/{userId}")
    public List<Story> getStoryByUserId(@PathVariable("userId") Integer userId) throws UserException {
        return storyService.getStoryByUserId(userId);
    }

    @GetMapping("/user")
    public List<Story> getStoryByUserToken(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return storyService.getStoryByUserId(user.getId());
    }
}