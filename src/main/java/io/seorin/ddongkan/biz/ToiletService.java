package io.seorin.ddongkan.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.seorin.ddongkan.dto.RaitingResponse;
import io.seorin.ddongkan.dto.ReviewRequest;
import io.seorin.ddongkan.dto.ReviewResponse;
import io.seorin.ddongkan.dto.ToiletDetailResponse;
import io.seorin.ddongkan.dto.ToiletResponse;
import io.seorin.ddongkan.entity.Review;
import io.seorin.ddongkan.repository.cache.RedisToiletRepository;
import io.seorin.ddongkan.repository.db.RaitingRepository;
import io.seorin.ddongkan.repository.db.ReviewRepository;
import io.seorin.ddongkan.repository.db.ToiletRepository;

@Service
public class ToiletService {
	private static final Logger log = LoggerFactory.getLogger(ToiletService.class);
	private final ToiletRepository toiletRepository;
	private final RedisToiletRepository redisToiletRepository;
	private final ReviewRepository reviewRepository;
	private final RaitingRepository raitingRepository;

	public ToiletService(ToiletRepository toiletRepository,
		RedisToiletRepository redisToiletRepository,
		ReviewRepository reviewRepository, RaitingRepository raitingRepository) {
		this.toiletRepository = toiletRepository;
		this.redisToiletRepository = redisToiletRepository;
		this.reviewRepository = reviewRepository;
		this.raitingRepository = raitingRepository;
	}

	public List<ToiletResponse> getToilets(Double lat, Double lng, Double radius) {
		var toilets = this.redisToiletRepository.findNearby(lat, lng, radius);
		var result = toilets.getContent().stream().map(ToiletResponse::fromCache)
			.toList();
		log.debug("toilet count within circle: {}", result.size());

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No toilets found nearby");
		}

		return result;
	}

	public ToiletDetailResponse getToiletDetail(Long id) {
		var toilet = this.toiletRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);
		log.debug("toilet found: {}", toilet.toString());
		return ToiletDetailResponse.from(toilet);
	}

	public void addReview(Long id, ReviewRequest reviewRequest) {
		var toilet = this.toiletRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);
		var review = Review.toiletReviewOf(toilet, reviewRequest);
		reviewRepository.save(review);
	}

	public RaitingResponse getRating(Long id) {

		var raiting = this.raitingRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + id)
			);

		return RaitingResponse.from(raiting);
	}

	public List<ReviewResponse> getReviews(Long id, Integer size, Integer index) {

		var pageable = PageRequest.of(index, size);
		var reviewPage = this.reviewRepository.findByToiletIdOrderByCreatedAtDesc(id, pageable);

		return reviewPage.map(ReviewResponse::from).getContent();
	}
}
