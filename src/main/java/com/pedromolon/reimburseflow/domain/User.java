package com.pedromolon.reimburseflow.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class User {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final Set<Role> roles;
    private final LocalDateTime createdAt;

    public User(Long id, String name, String email, String password, Set<Role> roles, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
