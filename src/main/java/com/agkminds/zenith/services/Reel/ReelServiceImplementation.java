package com.agkminds.zenith.services.Reel;

import com.agkminds.zenith.exceptions.ReelException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Reel;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.ReelRepository;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelServiceImplementation implements ReelService {

    @Autowired
    ReelRepository reelRepository;

    @Autowired
    UserService userService;

    @Override
    public Reel createReel(Reel reel, User user) throws UserException {
        if (user == null) {
            throw new UserException("User not found");
        }
        return reelRepository.save(reel);
    }

    @Override
    public List<Reel> findAllReels() throws ReelException {
        List<Reel> reels = reelRepository.findAll();
        if (reels.isEmpty()) {
            throw new ReelException("No reels found");
        }
        return reels;
    }

    @Override
    public List<Reel> findReelsByUserId(Integer userId) throws ReelException, UserException {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new UserException("User not found");
        }
        return reelRepository.findByUserId(userId);
    }
}