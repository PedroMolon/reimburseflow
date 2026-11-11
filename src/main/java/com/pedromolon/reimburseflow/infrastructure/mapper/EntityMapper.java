package com.pedromolon.reimburseflow.infrastructure.mapper;

import com.pedromolon.reimburseflow.domain.Money;
import com.pedromolon.reimburseflow.domain.Reimbursement;
import com.pedromolon.reimburseflow.domain.Role;
import com.pedromolon.reimburseflow.domain.User;
import com.pedromolon.reimburseflow.infrastructure.entity.ReimbursementEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.RoleEntity;
import com.pedromolon.reimburseflow.infrastructure.entity.UserEntity;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityMapper {

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;

        Set<RoleEntity> roleEntities = domain.getRoles() == null ? Collections.emptySet() :
                domain.getRoles()
                        .stream()
                        .map(EntityMapper::toEntity)
                        .collect(Collectors.toSet());

        return new UserEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPassword(),
                roleEntities,
                domain.getCreatedAt()
        );
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        Set<Role> roles = entity.getRoles() == null ? Collections.emptySet() :
                entity.getRoles()
                        .stream()
                        .map(EntityMapper::toDomain)
                        .collect(Collectors.toSet());

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                roles,
                entity.getCreatedAt()
        );
    }

    public static RoleEntity toEntity(Role domain) {
        if (domain == null) return null;

        return new RoleEntity(
                domain.getId(),
                domain.getName()
        );
    }

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) return null;

        return new Role(
                entity.getId(),
                entity.getName()
        );
    }

    public static ReimbursementEntity toEntity(Reimbursement domain, UserEntity userEntity) {
        if (domain == null) return null;

        return new ReimbursementEntity(
                domain.getId(),
                userEntity,
                domain.getDescription(),
                domain.getAmount().getAmount(),
                domain.getCategory(),
                domain.getStatus(),
                domain.getCreatedAt()
        );
    }

    public static Reimbursement toDomain(ReimbursementEntity entity) {
        if (entity == null) return null;

        return new Reimbursement(
                entity.getId(),
                entity.getUser().getId(),
                entity.getDescription(),
                new Money(entity.getAmount()),
                entity.getCategory(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
