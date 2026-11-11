package com.pedromolon.reimburseflow.domain.gateway;

import com.pedromolon.reimburseflow.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
