package com.agkminds.zenith.services.Story;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Story;
import com.agkminds.zenith.models.User;

import java.util.List;

public interface StoryService {
    Story createStory(Story story, User user);

    List<Story> getStoryByUserId(Integer userId) throws UserException;
}
