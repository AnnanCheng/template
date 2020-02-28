package com.annan.backend.persistence.dao;

import com.annan.backend.persistence.entity.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyJpaDao extends JpaRepository<MyEntity, Long> {
}
