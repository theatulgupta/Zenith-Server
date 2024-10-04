package com.agkminds.zenith.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String email;
    private String password;
    private String gender;
    private List<Integer> followers = new ArrayList<>();
    private List<Integer> followings = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<Post> savedPosts = new ArrayList<>();
}
