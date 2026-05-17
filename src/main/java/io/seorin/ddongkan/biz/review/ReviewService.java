package io.seorin.ddongkan.biz.review;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.seorin.ddongkan.dto.RaitingResponse;
import io.seorin.ddongkan.dto.ReviewRequest;
import io.seorin.ddongkan.dto.ReviewResponse;
import io.seorin.ddongkan.entity.Review;
import io.seorin.ddongkan.repository.db.RaitingRepository;
import io.seorin.ddongkan.repository.db.ReviewRepository;
import io.seorin.ddongkan.repository.db.ToiletRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ToiletRepository toiletRepository;
	private final RaitingRepository raitingRepository;

	public ReviewService(
		ReviewRepository reviewRepository,
		ToiletRepository toiletRepository,
		RaitingRepository raitingRepository
	) {
		this.reviewRepository = reviewRepository;
		this.toiletRepository = toiletRepository;
		this.raitingRepository = raitingRepository;
	}

	@CacheEvict(value = "reviews", key = "'latest:' + #id")
	public void addReview(Long toiletId, ReviewRequest reviewRequest) {
		var toilet = this.toiletRepository.findById(toiletId)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + toiletId)
			);
		var review = Review.toiletReviewOf(toilet, reviewRequest);
		reviewRepository.save(review);
	}

	public RaitingResponse getRating(Long toiletId) {

		var raiting = this.raitingRepository.findById(toiletId)
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Toilet not found with id: " + toiletId)
			);

		return RaitingResponse.from(raiting);
	}

	// 최신 100개 외의 리뷰를 조회하기 때문에 100개를 건너뛴 페이지부터 시작합니다.
	public List<ReviewResponse> getReviews(Long toiletId, Integer size, Integer index) {
		var pageable = PageRequest.of(index + (100 / size), size);
		var result = reviewRepository.findByToiletIdOrderByCreatedAtDesc(toiletId, pageable)
			.map(ReviewResponse::from)
			.getContent();

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for toilet id: " + toiletId);
		}

		return result;
	}

	@Cacheable(value = "reviews", key = "'latest:' + #toiletId", sync = true)
	public List<ReviewResponse> getTop100Reviews(Long toiletId) {
		var pageable = PageRequest.of(0, 100);
		var result = reviewRepository.findByToiletIdOrderByCreatedAtDesc(toiletId, pageable)
			.map(ReviewResponse::from)
			.getContent();

		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for toilet id: " + toiletId);
		}

		return result;
	}
}
