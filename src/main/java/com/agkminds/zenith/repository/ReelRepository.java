package com.agkminds.zenith.repository;

import com.agkminds.zenith.models.Reel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Integer> {
    List<Reel> findByUserId(Integer userId);
}
