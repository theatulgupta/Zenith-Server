package com.agkminds.zenith.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    private User user;

    @ManyToMany
    private List<User> likedBy = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}