package io.seorin.ddongkan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.seorin.ddongkan.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
