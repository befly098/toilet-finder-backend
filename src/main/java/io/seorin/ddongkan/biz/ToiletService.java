package io.seorin.ddongkan.biz;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.seorin.ddongkan.config.GeomFactory;
import io.seorin.ddongkan.dto.ReviewRequest;
import io.seorin.ddongkan.entity.Review;
import io.seorin.ddongkan.entity.Toilet;
import io.seorin.ddongkan.repository.ReviewRepository;
import io.seorin.ddongkan.repository.ToiletRepository;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	private final GeomFactory geomFactory;
	private final ToiletRepository toiletRepository;
	private final ReviewRepository reviewRepository;

	public ToiletService(GeomFactory geomFactory, ToiletRepository toiletRepository,
		ReviewRepository reviewRepository) {
		this.geomFactory = geomFactory;
		this.toiletRepository = toiletRepository;
		this.reviewRepository = reviewRepository;
	}

	public List<Toilet> selectAllPointsWithinRadius(Double lat, Double lng, Double radius) {
		var userPoint = geomFactory.getGeometryFactory().createPoint(new Coordinate(lng, lat));
		List<Toilet> result = this.toiletRepository.findToilets(userPoint, radius);
		log.info("toilet count within circle: {}", result.size());
		return result;
	}

	public Toilet getToiletById(Long id) {
		return this.toiletRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Toilet not found with id: " + id)
		);
	}

	public void addReview(Long id, ReviewRequest reviewRequest) {
		var toilet = this.getToiletById(id);
		var review = Review.toiletReviewOf(toilet, reviewRequest);
		reviewRepository.save(review);
	}
}
