package io.seorin.ddongkan.repository.db;

import org.springframework.data.jpa.repository.JpaRepository;

import io.seorin.ddongkan.entity.Rating;

public interface RaitingRepository extends JpaRepository<Rating, Long> {
}
