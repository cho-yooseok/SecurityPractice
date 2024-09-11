package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;
    private String password;
    private String name;
    private int age;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)  // M:N
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name="member_id"), // FK
            inverseJoinColumns = @JoinColumn(name="role_id") // FK
    )
    private Set<Role> roles;
}
