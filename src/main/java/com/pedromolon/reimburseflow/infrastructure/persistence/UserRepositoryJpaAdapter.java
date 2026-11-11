package com.pedromolon.reimburseflow.infrastructure.persistence;

import com.pedromolon.reimburseflow.domain.User;
import com.pedromolon.reimburseflow.domain.gateway.UserRepository;
import com.pedromolon.reimburseflow.infrastructure.mapper.EntityMapper;
import com.pedromolon.reimburseflow.infrastructure.repository.SpringUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryJpaAdapter implements UserRepository {

    private final SpringUserRepository springUserRepository;

    public UserRepositoryJpaAdapter(SpringUserRepository springUserRepository) {
        this.springUserRepository = springUserRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return springUserRepository.findById(id)
                .map(EntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springUserRepository.findByEmail(email)
                .map(EntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return EntityMapper.toDomain(
                springUserRepository.save(
                        EntityMapper.toEntity(user)
                )
        );
    }
}
