package com.agkminds.zenith.services.Reel;

import com.agkminds.zenith.exceptions.ReelException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Reel;
import com.agkminds.zenith.models.User;

import java.util.List;

public interface ReelService {
    Reel createReel(Reel reel, User user) throws ReelException, UserException;

    List<Reel> findAllReels() throws ReelException;

    List<Reel> findReelsByUserId(Integer userId) throws ReelException, UserException;
}