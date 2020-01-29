package com.annan.backend.persistence.dao;

import com.annan.backend.persistence.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinateDao extends JpaRepository<Coordinate, Long> {
    Optional<Coordinate> findByPostCode(String postCode);
}
