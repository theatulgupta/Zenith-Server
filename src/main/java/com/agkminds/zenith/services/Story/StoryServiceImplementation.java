package com.agkminds.zenith.services.Story;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Story;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.StoryRepository;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public Story createStory(Story story, User user) {
        Story newStory = new Story();
        newStory.setCaption(story.getCaption());
        newStory.setMedia(story.getMedia());
        newStory.setUser(user);
        newStory.setTimestamp(LocalDateTime.now());
        return storyRepository.save(newStory);
    }

    @Override
    public List<Story> getStoryByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found with id: " + userId);
        }
        return storyRepository.findByUserId(userId);
    }
}