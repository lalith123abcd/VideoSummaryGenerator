package com.example.VideoToSummaryGenerator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor
public class User {


    public User (String email,String password){

        this.email=email;
        this.password=password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @NotBlank
    @Size(max = 20)
    @Column(unique = true)

    private String email;

    @NotBlank
    @Size(min=8)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)

    private Set<Role> roles;


    public enum Role {
        USER,
        ADMIN

    }}


