package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.ReelException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Reel;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Reel.ReelService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reel")
public class ReelController {

    private final ReelService reelService;
    private final UserService userService;

    @Autowired
    public ReelController(ReelService reelService, UserService userService) {
        this.reelService = reelService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public Reel createReel(@RequestBody Reel reel, @RequestHeader("Authorization") String jwt) throws ReelException, UserException {
        User user = userService.findUserByJwt(jwt);
        return reelService.createReel(reel, user);
    }

    @GetMapping("/all")
    public List<Reel> getAllReels() throws ReelException {
        return reelService.findAllReels();
    }

    @GetMapping
    public List<Reel> getReelByUserId(@RequestHeader("Authorization") String jwt) throws ReelException, UserException {
        User user = userService.findUserByJwt(jwt);
        return reelService.findReelsByUserId(user.getId());
    }
}